#!/usr/bin/env bash
# Inspect an original JAR: manifest, bytecode version, layout, entry points.
set -euo pipefail
# shellcheck source=common.sh
source "$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)/common.sh"

need_cmd java
need_cmd jar
need_cmd python3

JAR_PATH="$(find_original_jar "${1:-}")"
mkdir -p "${META_DIR}" "${WORK_DIR}/extracted"
rm -rf "${WORK_DIR}/extracted"
mkdir -p "${WORK_DIR}/extracted"

echo "== JAR =="
echo "path: ${JAR_PATH}"
echo "size: $(du -h "${JAR_PATH}" | cut -f1)"
echo

echo "== MANIFEST =="
if jar xf "${JAR_PATH}" -C "${WORK_DIR}/extracted" META-INF/MANIFEST.MF 2>/dev/null; then
  :
fi
# Full extract for layout detection
unzip -qo "${JAR_PATH}" -d "${WORK_DIR}/extracted"
MANIFEST="${WORK_DIR}/extracted/META-INF/MANIFEST.MF"
if [[ -f "${MANIFEST}" ]]; then
  cat "${MANIFEST}"
  cp "${MANIFEST}" "${META_DIR}/MANIFEST.MF"
else
  echo "(no META-INF/MANIFEST.MF)"
fi
echo

MAIN_CLASS="$(read_manifest_value "${MANIFEST}" "Main-Class")"
BUNDLE_NAME="$(read_manifest_value "${MANIFEST}" "Bundle-SymbolicName")"
START_CLASS="$(read_manifest_value "${MANIFEST}" "Start-Class")"

echo "== ENTRY POINTS =="
echo "Main-Class: ${MAIN_CLASS:-"(none)"}"
echo "Start-Class: ${START_CLASS:-"(none)"}"
echo "Bundle-SymbolicName: ${BUNDLE_NAME:-"(none)"}"
echo

echo "== LAYOUT =="
if [[ -d "${WORK_DIR}/extracted/BOOT-INF" ]]; then
  echo "type: Spring Boot fat JAR"
elif [[ -n "${BUNDLE_NAME}" ]] || [[ -f "${WORK_DIR}/extracted/plugin.xml" ]]; then
  echo "type: OSGi / Eclipse plugin JAR"
elif [[ -d "${WORK_DIR}/extracted/WEB-INF" ]]; then
  echo "type: WAR-style package"
else
  echo "type: standard JAR"
fi

NESTED_JARS="$(find "${WORK_DIR}/extracted" -name "*.jar" | wc -l | tr -d ' ')"
echo "nested JARs: ${NESTED_JARS}"
CLASS_COUNT="$(find "${WORK_DIR}/extracted" -name "*.class" | wc -l | tr -d ' ')"
echo "class files: ${CLASS_COUNT}"
echo

read -r MAX_MAJOR CLASS_COUNTED <<<"$(detect_max_class_major "${WORK_DIR}/extracted")"
JAVA_VER="$(class_major_to_java "${MAX_MAJOR}")"
echo "== BYTECODE =="
echo "highest class major: ${MAX_MAJOR} (Java ${JAVA_VER})"
echo "readable class files: ${CLASS_COUNTED}"
echo "${JAVA_VER}" > "${META_DIR}/java-version.txt"
echo "${MAX_MAJOR}" > "${META_DIR}/class-major.txt"
[[ -n "${MAIN_CLASS}" ]] && echo "${MAIN_CLASS}" > "${META_DIR}/main-class.txt"
echo

echo "== TOP PACKAGES =="
find "${WORK_DIR}/extracted" -name "*.class" \
  | sed "s|^${WORK_DIR}/extracted/||" \
  | grep -v "^BOOT-INF/lib/" \
  | awk -F/ 'NF>1 { OFS="/"; NF=NF-1; print }' \
  | sed 's|^BOOT-INF/classes/||' \
  | sort | uniq -c | sort -nr | head -20 || true
echo

echo "== SAMPLE CLASSES =="
find "${WORK_DIR}/extracted" -name "*.class" \
  | grep -v "/BOOT-INF/lib/" \
  | sed "s|^${WORK_DIR}/extracted/||; s|\.class$||; s|/|.|g; s|^BOOT-INF.classes.||" \
  | head -20 || true
echo

{
  echo "jar=$(basename "${JAR_PATH}")"
  echo "java=${JAVA_VER}"
  echo "main=${MAIN_CLASS}"
  echo "classes=${CLASS_COUNT}"
  echo "nested_jars=${NESTED_JARS}"
} > "${META_DIR}/inspect.properties"

echo "Wrote metadata to ${META_DIR}"
echo "Extracted contents: ${WORK_DIR}/extracted"
echo
echo "Next: ./scripts/decompile.sh"
