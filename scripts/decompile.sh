#!/usr/bin/env bash
# Decompile the original JAR into src/main/java and extract resources.
# Vineflower is the primary decompiler; CFR output is kept as a reference.
set -euo pipefail
# shellcheck source=common.sh
source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/common.sh"

need_cmd java
need_cmd unzip
need_cmd python3

[[ -f "${VINEFLOWER_JAR}" ]] || die "missing ${VINEFLOWER_JAR}"
[[ -f "${CFR_JAR}" ]] || die "missing ${CFR_JAR}"

JAR_PATH="$(find_original_jar "${1:-}")"
mkdir -p "${WORK_DIR}/extracted" "${WORK_DIR}/decompiled-cfr" "${META_DIR}" \
  "${SRC_JAVA}" "${SRC_RES}"

echo "Extracting $(basename "${JAR_PATH}") ..."
rm -rf "${WORK_DIR}/extracted"
mkdir -p "${WORK_DIR}/extracted"
unzip -qo "${JAR_PATH}" -d "${WORK_DIR}/extracted"

# Prefer application classes for fat JARs; otherwise decompile the whole tree.
DECOMPILE_INPUT="${WORK_DIR}/extracted"
if [[ -d "${WORK_DIR}/extracted/BOOT-INF/classes" ]]; then
  DECOMPILE_INPUT="${WORK_DIR}/extracted/BOOT-INF/classes"
  echo "Detected Spring Boot layout; decompiling BOOT-INF/classes"
fi

echo "Decompiling with Vineflower -> ${SRC_JAVA}"
rm -rf "${SRC_JAVA:?}/"*
mkdir -p "${SRC_JAVA}"
# Vineflower writes Java sources into the destination folder.
java -jar "${VINEFLOWER_JAR}" \
  --folder \
  --silent \
  "${DECOMPILE_INPUT}" \
  "${SRC_JAVA}"

# Vineflower copies resources into the output as well. Move non-Java files to resources.
echo "Separating resources from decompiled sources ..."
python3 - "${SRC_JAVA}" "${SRC_RES}" <<'PY'
import os, shutil, sys
src, res = sys.argv[1], sys.argv[2]
os.makedirs(res, exist_ok=True)
for dirpath, dirnames, filenames in os.walk(src):
    rel = os.path.relpath(dirpath, src)
    for name in filenames:
        if name.endswith(".java"):
            continue
        from_path = os.path.join(dirpath, name)
        to_dir = os.path.join(res, rel) if rel != "." else res
        os.makedirs(to_dir, exist_ok=True)
        shutil.move(from_path, os.path.join(to_dir, name))
# Drop empty dirs left behind (except package dirs that still have java)
for dirpath, dirnames, filenames in os.walk(src, topdown=False):
    if dirpath == src:
        continue
    try:
        os.rmdir(dirpath)
    except OSError:
        pass
PY

# Also copy original non-class resources that Vineflower may have skipped.
echo "Copying original non-class resources ..."
python3 - "${WORK_DIR}/extracted" "${SRC_RES}" <<'PY'
import os, shutil, sys
extracted, res = sys.argv[1], sys.argv[2]
skip_prefixes = ("BOOT-INF/lib/",)
class_root = extracted
boot_classes = os.path.join(extracted, "BOOT-INF", "classes")
if os.path.isdir(boot_classes):
    # Copy BOOT-INF/classes resources and top-level META-INF
    roots = [boot_classes, os.path.join(extracted, "META-INF")]
else:
    roots = [extracted]
for root in roots:
    if not os.path.exists(root):
        continue
    for dirpath, _, filenames in os.walk(root):
        rel_from_extracted = os.path.relpath(dirpath, extracted).replace("\\", "/")
        if rel_from_extracted.startswith("BOOT-INF/lib"):
            continue
        if rel_from_extracted.startswith("BOOT-INF/classes/"):
            rel = rel_from_extracted[len("BOOT-INF/classes/"):]
        elif rel_from_extracted == "BOOT-INF/classes":
            rel = "."
        else:
            rel = rel_from_extracted
        for name in filenames:
            if name.endswith(".class") or name.endswith(".java"):
                continue
            from_path = os.path.join(dirpath, name)
            to_dir = os.path.join(res, rel) if rel not in (".", "") else res
            os.makedirs(to_dir, exist_ok=True)
            dest = os.path.join(to_dir, name)
            if not os.path.exists(dest):
                shutil.copy2(from_path, dest)
PY

echo "Decompiling with CFR (reference only) -> work/decompiled-cfr"
rm -rf "${WORK_DIR}/decompiled-cfr"
mkdir -p "${WORK_DIR}/decompiled-cfr"
java -jar "${CFR_JAR}" "${JAR_PATH}" \
  --outputdir "${WORK_DIR}/decompiled-cfr" \
  --silent true \
  --comments false \
  >/dev/null

JAVA_FILES="$(find "${SRC_JAVA}" -name "*.java" | wc -l | tr -d ' ')"
echo "Decompiled ${JAVA_FILES} Java source file(s)."

MANIFEST="${WORK_DIR}/extracted/META-INF/MANIFEST.MF"
MAIN_CLASS="$(read_manifest_value "${MANIFEST}" "Main-Class")"
START_CLASS="$(read_manifest_value "${MANIFEST}" "Start-Class")"
EFFECTIVE_MAIN="${START_CLASS:-${MAIN_CLASS}}"
read -r MAX_MAJOR CLASS_COUNTED <<<"$(detect_max_class_major "${WORK_DIR}/extracted")"
JAVA_VER="$(class_major_to_java "${MAX_MAJOR}")"
# Maven compiler plugin wants 1.8 not 8 for older values; 8+ is fine as 8/11/17/21
case "${JAVA_VER}" in
  1.*|5|6|7) MVN_JAVA="8" ;;
  unknown*) MVN_JAVA="8" ;;
  *) MVN_JAVA="${JAVA_VER}" ;;
esac

ARTIFACT="$(basename "${JAR_PATH}" .jar | tr '[:upper:]' '[:lower:]' | tr -c 'a-z0-9._-' '-')"
VERSION="$(read_manifest_value "${MANIFEST}" "Implementation-Version")"
[[ -z "${VERSION}" ]] && VERSION="$(read_manifest_value "${MANIFEST}" "Bundle-Version")"
[[ -z "${VERSION}" ]] && VERSION="1.0.0-SNAPSHOT"

cat > "${ROOT}/pom.xml" <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>cn.njmetro.switchcabinet</groupId>
  <artifactId>${ARTIFACT}</artifactId>
  <version>${VERSION}</version>
  <name>njmetroSwitchCabinet</name>
  <description>Decompiled and rebuilt Nanjing Metro switch-cabinet package</description>
  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.release>${MVN_JAVA}</maven.compiler.release>
  </properties>
  <build>
    <plugins>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.13.0</version>
      </plugin>
      <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.4.2</version>
        <configuration>
          <archive>
            <manifest>
              <addDefaultImplementationEntries>true</addDefaultImplementationEntries>
              <mainClass>${EFFECTIVE_MAIN}</mainClass>
            </manifest>
            <manifestFile>src/main/resources/META-INF/MANIFEST.MF</manifestFile>
          </archive>
        </configuration>
      </plugin>
    </plugins>
  </build>
</project>
EOF

mkdir -p "${META_DIR}"
cp "${MANIFEST}" "${META_DIR}/MANIFEST.MF" 2>/dev/null || true
echo "${JAR_PATH}" > "${META_DIR}/original-jar.path"
echo "${JAVA_VER}" > "${META_DIR}/java-version.txt"
[[ -n "${EFFECTIVE_MAIN}" ]] && echo "${EFFECTIVE_MAIN}" > "${META_DIR}/main-class.txt"

echo
echo "Sources:    ${SRC_JAVA}"
echo "Resources:  ${SRC_RES}"
echo "CFR backup: ${WORK_DIR}/decompiled-cfr"
echo "POM:        ${ROOT}/pom.xml"
echo
echo "Edit the Java sources to fix bugs, then run: ./scripts/rebuild.sh"
