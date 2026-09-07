# njmetroSwitchCabinet

Decompiled, patched, and rebuilt Nanjing Metro switch-cabinet monitoring JAR (`com.drsi.njmsc`).

| Artifact | Path |
|----------|------|
| Original | `original/nj-metroSwitchCabinet.jar` |
| Patched release | `dist/nj-metroSwitchCabinet-patched.jar` |
| Bug list | `BUGS.md` |

Main class: `com.drsi.njmsc.NjMetroSwitchCabinetApplication` (Spring Boot 2.7.3, Java 8). Deploy the patched JAR the same way as the original (with the existing `libs/` directory from Class-Path).

---

## 中文说明

原始包已反编译到 `src/main/java`。修复说明见 `BUGS.md`。发布包：

`dist/nj-metroSwitchCabinet-patched.jar`

替换现场原来的 JAR 即可，依赖 `libs/` 目录保持不变。

---

## Rebuild after further edits

```bash
./scripts/inspect-jar.sh
./scripts/decompile.sh          # only if you need a fresh decompile
# edit src/main/java
./scripts/rebuild.sh            # overlays compiled classes onto the original JAR
```

## Layout

| Path | Purpose |
|------|---------|
| `original/` | Vendor JAR |
| `src/main/java` | Decompiled / patched Java |
| `src/main/resources` | Config, COMTRADE sample JSON |
| `src/compile-stubs` | Compile-only stubs for private `com.zhixin` / `com.dsri` APIs |
| `dist/` | Patched release JAR |
| `tools/` | Vineflower + CFR |
| `work/` | Intermediate output (gitignored) |
