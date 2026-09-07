#!/usr/bin/env bash
# Shared paths and helpers for the decompile / rebuild pipeline.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
ORIGINAL_DIR="${ROOT}/original"
TOOLS_DIR="${ROOT}/tools"
WORK_DIR="${ROOT}/work"
SRC_JAVA="${ROOT}/src/main/java"
SRC_RES="${ROOT}/src/main/resources"
DIST_DIR="${ROOT}/dist"
META_DIR="${WORK_DIR}/meta"

VINEFLOWER_JAR="${TOOLS_DIR}/vineflower-1.12.0.jar"
CFR_JAR="${TOOLS_DIR}/cfr-0.152.jar"

die() {
  echo "error: $*" >&2
  exit 1
}

need_cmd() {
  command -v "$1" >/dev/null 2>&1 || die "required command not found: $1"
}

find_original_jar() {
  local specified="${1:-}"
  if [[ -n "${specified}" ]]; then
    [[ -f "${specified}" ]] || die "JAR not found: ${specified}"
    echo "$(cd "$(dirname "${specified}")" && pwd)/$(basename "${specified}")"
    return
  fi

  shopt -s nullglob
  local jars=("${ORIGINAL_DIR}"/*.jar)
  shopt -u nullglob
  if [[ ${#jars[@]} -eq 0 ]]; then
    die "no JAR found in ${ORIGINAL_DIR}. Copy the original package there, then re-run."
  fi
  if [[ ${#jars[@]} -gt 1 ]]; then
    echo "multiple JARs found in ${ORIGINAL_DIR}:" >&2
    printf '  %s\n' "${jars[@]}" >&2
    die "pass the JAR path explicitly: $0 path/to/app.jar"
  fi
  echo "${jars[0]}"
}

class_major_to_java() {
  local major="$1"
  case "${major}" in
    45) echo "1.1" ;;
    46) echo "1.2" ;;
    47) echo "1.3" ;;
    48) echo "1.4" ;;
    49) echo "5" ;;
    50) echo "6" ;;
    51) echo "7" ;;
    52) echo "8" ;;
    53) echo "9" ;;
    54) echo "10" ;;
    55) echo "11" ;;
    56) echo "12" ;;
    57) echo "13" ;;
    58) echo "14" ;;
    59) echo "15" ;;
    60) echo "16" ;;
    61) echo "17" ;;
    62) echo "18" ;;
    63) echo "19" ;;
    64) echo "20" ;;
    65) echo "21" ;;
    66) echo "22" ;;
    67) echo "23" ;;
    68) echo "24" ;;
    69) echo "25" ;;
    *) echo "unknown(${major})" ;;
  esac
}

detect_max_class_major() {
  local extracted="$1"
  python3 - "$extracted" <<'PY'
import os, struct, sys
root = sys.argv[1]
max_major = 0
count = 0
for dirpath, _, files in os.walk(root):
    for name in files:
        if not name.endswith(".class"):
            continue
        path = os.path.join(dirpath, name)
        try:
            with open(path, "rb") as fh:
                magic, minor, major = struct.unpack(">IHH", fh.read(8))
            if magic != 0xCAFEBABE:
                continue
            count += 1
            if major > max_major:
                max_major = major
        except Exception:
            continue
print(f"{max_major} {count}")
PY
}

read_manifest_value() {
  local manifest="$1"
  local key="$2"
  [[ -f "${manifest}" ]] || return 0
  python3 - "$manifest" "$key" <<'PY'
import sys
path, key = sys.argv[1], sys.argv[2]
text = open(path, encoding="utf-8", errors="replace").read()
# Unfold continuation lines (leading space)
lines = []
for raw in text.splitlines():
    if raw.startswith(" ") and lines:
        lines[-1] += raw[1:]
    else:
        lines.append(raw)
prefix = key + ": "
for line in lines:
    if line.startswith(prefix):
        print(line[len(prefix):].strip())
        break
PY
}

strip_jar_signatures() {
  local dir="$1"
  find "${dir}/META-INF" -maxdepth 1 -type f \( \
    -name "*.SF" -o -name "*.RSA" -o -name "*.DSA" -o -name "*.EC" \
  \) -delete 2>/dev/null || true
  if [[ -f "${dir}/META-INF/MANIFEST.MF" ]]; then
    python3 - "${dir}/META-INF/MANIFEST.MF" <<'PY'
import sys
path = sys.argv[1]
lines = open(path, encoding="utf-8", errors="replace").read().splitlines(True)
out = []
skip_digest = False
for line in lines:
    if skip_digest:
        if line.startswith(" ") or line.lower().startswith("sha") or line.lower().startswith("digest"):
            continue
        skip_digest = False
    if line.startswith("Name: ") or line.startswith("SHA1-Digest:") or line.startswith("SHA-256-Digest:"):
        skip_digest = True
        continue
    out.append(line)
open(path, "w", encoding="utf-8").writelines(out)
PY
  fi
}
