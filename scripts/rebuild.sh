#!/usr/bin/env bash
# Rebuild a release JAR.
#
# Default (overlay) mode recompiles src/main/java against the original JAR and
# copies the new .class files on top of the original package. That is the
# reliable way to ship a patched release when decompiled sources do not all
# compile cleanly.
#
#   ./scripts/rebuild.sh              # overlay patched classes
#   ./scripts/rebuild.sh --full       # Maven package from sources only
#   ./scripts/rebuild.sh --jar PATH   # original JAR path
set -euo pipefail
# shellcheck source=common.sh
source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/common.sh"

need_cmd java
need_cmd javac
need_cmd jar
need_cmd unzip
need_cmd zip
need_cmd python3

MODE="overlay"
JAR_ARG=""
while [[ $# -gt 0 ]]; do
  case "$1" in
    --full) MODE="full"; shift ;;
    --overlay) MODE="overlay"; shift ;;
    --jar)
      JAR_ARG="${2:-}"
      shift 2
      ;;
    -h|--help)
      sed -n '2,16p' "$0"
      exit 0
      ;;
    *)
      JAR_ARG="$1"
      shift
      ;;
  esac
done

mkdir -p "${DIST_DIR}" "${WORK_DIR}/compile" "${WORK_DIR}/rebuild"

if [[ "${MODE}" == "full" ]]; then
  need_cmd mvn
  [[ -f "${ROOT}/pom.xml" ]] || die "pom.xml missing; run ./scripts/decompile.sh first"
  echo "Full Maven package ..."
  (cd "${ROOT}" && mvn -q -DskipTests package)
  built="$(find "${ROOT}/target" -maxdepth 1 -name "*.jar" ! -name "*sources*" ! -name "*javadoc*" | head -1)"
  [[ -n "${built}" ]] || die "Maven did not produce a JAR"
  cp "${built}" "${DIST_DIR}/$(basename "${built}")"
  echo "Wrote ${DIST_DIR}/$(basename "${built}")"
  exit 0
fi

if [[ -f "${META_DIR}/original-jar.path" ]]; then
  ORIG="$(cat "${META_DIR}/original-jar.path")"
fi
if [[ -n "${JAR_ARG}" ]]; then
  ORIG="$(find_original_jar "${JAR_ARG}")"
elif [[ -z "${ORIG:-}" || ! -f "${ORIG:-}" ]]; then
  ORIG="$(find_original_jar "")"
fi

[[ -d "${SRC_JAVA}" ]] || die "no sources at ${SRC_JAVA}; run ./scripts/decompile.sh first"
JAVA_FILES="$(find "${SRC_JAVA}" -name "*.java" | wc -l | tr -d ' ')"
[[ "${JAVA_FILES}" -gt 0 ]] || die "no .java files under ${SRC_JAVA}"

JAVA_VER="8"
if [[ -f "${META_DIR}/java-version.txt" ]]; then
  JAVA_VER="$(tr -d '[:space:]' < "${META_DIR}/java-version.txt")"
fi
case "${JAVA_VER}" in
  1.*|5|6|7) RELEASE_FLAG="8" ;;
  unknown*) RELEASE_FLAG="8" ;;
  *) RELEASE_FLAG="${JAVA_VER}" ;;
esac

echo "Original JAR: ${ORIG}"
echo "Compiling ${JAVA_FILES} source file(s) for Java ${RELEASE_FLAG} ..."

rm -rf "${WORK_DIR}/compile"
mkdir -p "${WORK_DIR}/compile"

# Build a classpath from the original JAR plus any nested JARs.
CP="${ORIG}"
NESTED_LIB="${WORK_DIR}/nested-libs"
rm -rf "${NESTED_LIB}"
mkdir -p "${NESTED_LIB}"
unzip -qo "${ORIG}" "*.jar" -d "${NESTED_LIB}" 2>/dev/null || true
while IFS= read -r nested; do
  CP="${CP}:${nested}"
done < <(find "${NESTED_LIB}" -name "*.jar" | sort)

SOURCES_LIST="${WORK_DIR}/sources.list"
find "${SRC_JAVA}" -name "*.java" > "${SOURCES_LIST}"

set +e
javac --release "${RELEASE_FLAG}" \
  -encoding UTF-8 \
  -cp "${CP}" \
  -d "${WORK_DIR}/compile" \
  @"${SOURCES_LIST}" 2>"${WORK_DIR}/javac.err"
JAVAC_STATUS=$?
set -e

if [[ "${JAVAC_STATUS}" -ne 0 ]]; then
  echo "Full source compile failed (common with decompiled trees)."
  echo "Retrying compile of each package that succeeds, then overlaying those classes."
  echo "javac errors: ${WORK_DIR}/javac.err"
  # Compile file-by-file so a few broken decompiled classes do not block patches.
  COMPILED=0
  FAILED=0
  while IFS= read -r src; do
    if javac --release "${RELEASE_FLAG}" \
      -encoding UTF-8 \
      -cp "${CP}:${WORK_DIR}/compile" \
      -d "${WORK_DIR}/compile" \
      "${src}" 2>>"${WORK_DIR}/javac-partial.err"; then
      COMPILED=$((COMPILED + 1))
    else
      FAILED=$((FAILED + 1))
    fi
  done < "${SOURCES_LIST}"
  echo "Partial compile: ${COMPILED} ok, ${FAILED} failed"
  [[ "${COMPILED}" -gt 0 ]] || die "no classes compiled; see ${WORK_DIR}/javac.err"
fi

echo "Overlaying compiled classes onto original JAR ..."
rm -rf "${WORK_DIR}/rebuild"
mkdir -p "${WORK_DIR}/rebuild"
unzip -qo "${ORIG}" -d "${WORK_DIR}/rebuild"
strip_jar_signatures "${WORK_DIR}/rebuild"

CLASS_ROOT="${WORK_DIR}/rebuild"
if [[ -d "${WORK_DIR}/rebuild/BOOT-INF/classes" ]]; then
  CLASS_ROOT="${WORK_DIR}/rebuild/BOOT-INF/classes"
fi

# Copy recompiled classes
cp -a "${WORK_DIR}/compile/." "${CLASS_ROOT}/"

# Overlay resources (skip a rewritten MANIFEST if original exists; keep original then strip sigs)
if [[ -d "${SRC_RES}" ]]; then
  python3 - "${SRC_RES}" "${WORK_DIR}/rebuild" "${CLASS_ROOT}" <<'PY'
import os, shutil, sys
src_res, rebuild, class_root = sys.argv[1], sys.argv[2], sys.argv[3]
for dirpath, _, filenames in os.walk(src_res):
    rel = os.path.relpath(dirpath, src_res)
    for name in filenames:
        from_path = os.path.join(dirpath, name)
        rel_path = name if rel == "." else os.path.join(rel, name)
        # META-INF stays at JAR root even for Spring Boot
        if rel_path.replace("\\", "/").startswith("META-INF/"):
            dest = os.path.join(rebuild, rel_path)
        else:
            dest = os.path.join(class_root, rel_path)
        os.makedirs(os.path.dirname(dest), exist_ok=True)
        shutil.copy2(from_path, dest)
PY
fi
strip_jar_signatures "${WORK_DIR}/rebuild"

BASE="$(basename "${ORIG}" .jar)"
OUT="${DIST_DIR}/${BASE}-patched.jar"
rm -f "${OUT}"
(
  cd "${WORK_DIR}/rebuild"
  zip -qr "${OUT}" .
)

echo
echo "Release JAR: ${OUT}"
echo "size: $(du -h "${OUT}" | cut -f1)"
if [[ -f "${META_DIR}/main-class.txt" ]]; then
  echo "Main-Class: $(cat "${META_DIR}/main-class.txt")"
  echo "Run: java -jar ${OUT}"
fi
