#!/usr/bin/env bash
# End-to-end check of inspect -> decompile -> fix -> overlay-rebuild.
# Uses a throwaway app with a real bug; does not touch repo sources.
set -euo pipefail

ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
TMP="$(mktemp -d /tmp/jar-pipeline-selftest.XXXXXX)"
cleanup() { rm -rf "${TMP}"; }
trap cleanup EXIT

echo "== Selftest workspace: ${TMP}"
mkdir -p "${TMP}/src/cn/njmetro/cabinet" "${TMP}/original" "${TMP}/scripts" "${TMP}/tools"

# App with an off-by-one bug: sum of 1..n wrongly stops at n-1.
cat > "${TMP}/src/cn/njmetro/cabinet/CabinetMonitor.java" <<'JAVA'
package cn.njmetro.cabinet;

public final class CabinetMonitor {
    public static int sumTo(int n) {
        int total = 0;
        // BUG: uses < n instead of <= n
        for (int i = 1; i < n; i++) {
            total += i;
        }
        return total;
    }

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        System.out.println(sumTo(n));
    }
}
JAVA

mkdir -p "${TMP}/src/META-INF"
cat > "${TMP}/src/META-INF/MANIFEST.MF" <<'MF'
Manifest-Version: 1.0
Main-Class: cn.njmetro.cabinet.CabinetMonitor
Implementation-Title: cabinet-monitor
Implementation-Version: 0.0.1-test

MF
javac -encoding UTF-8 -d "${TMP}/classes" "${TMP}/src/cn/njmetro/cabinet/CabinetMonitor.java"
mkdir -p "${TMP}/classes/META-INF"
cp "${TMP}/src/META-INF/MANIFEST.MF" "${TMP}/classes/META-INF/MANIFEST.MF"
jar cfm "${TMP}/original/cabinet-monitor.jar" "${TMP}/classes/META-INF/MANIFEST.MF" -C "${TMP}/classes" .

BUGGY="$(java -jar "${TMP}/original/cabinet-monitor.jar" 10)"
echo "Buggy output for n=10: ${BUGGY}"
[[ "${BUGGY}" == "45" ]] || { echo "expected buggy output 45, got ${BUGGY}"; exit 1; }

# Point scripts at this temp tree by copying the pipeline.
cp "${ROOT}/scripts/"*.sh "${TMP}/scripts/"
cp "${ROOT}/tools/"*.jar "${TMP}/tools/"
mkdir -p "${TMP}/src/main/java" "${TMP}/src/main/resources" "${TMP}/dist" "${TMP}/work"

echo "== inspect"
bash "${TMP}/scripts/inspect-jar.sh"

echo "== decompile"
bash "${TMP}/scripts/decompile.sh"

DECOMPILED="${TMP}/src/main/java/cn/njmetro/cabinet/CabinetMonitor.java"
[[ -f "${DECOMPILED}" ]] || { echo "decompile did not produce CabinetMonitor.java"; find "${TMP}/src" -type f | head; exit 1; }

echo "== fix bug in decompiled source"
python3 - "${DECOMPILED}" <<'PY'
import re, sys
path = sys.argv[1]
text = open(path, encoding="utf-8").read()
# Original source used i < n; Vineflower often renames to var2 < var0.
replaced = text
replaced = replaced.replace("i < n", "i <= n").replace("i<n", "i<=n")
replaced = re.sub(r"(for\s*\([^;]+;\s*[^;]+)\s*<\s*([^;]+)(;)", r"\1 <= \2\3", replaced)
if "<= " not in replaced.split("sumTo", 1)[-1].split("main", 1)[0]:
    raise SystemExit("could not patch sumTo in decompiled source:\n" + text)
open(path, "w", encoding="utf-8").write(replaced)
print("patched", path)
print(replaced)
PY

echo "== rebuild overlay"
bash "${TMP}/scripts/rebuild.sh"

PATCHED="$(ls "${TMP}/dist"/*.jar | head -1)"
FIXED="$(java -jar "${PATCHED}" 10)"
echo "Patched output for n=10: ${FIXED}"
[[ "${FIXED}" == "55" ]] || { echo "expected 55 after fix, got ${FIXED}"; exit 1; }

echo
echo "SELFTEST PASSED"
echo "  original (buggy) : ${BUGGY}"
echo "  patched (fixed)  : ${FIXED}"
echo "  release jar      : ${PATCHED}"
