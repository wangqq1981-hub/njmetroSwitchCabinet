# njmetroSwitchCabinet

Decompile a Java JAR, fix bugs in the recovered sources, and build a patched release JAR.

The original application JAR was **not attached** to this workspace. Drop it into `original/` and run the pipeline below.

---

## 中文说明

把原始 JAR 放到 `original/` 目录，然后执行：

```bash
./scripts/inspect-jar.sh
./scripts/decompile.sh
# 在 src/main/java 里修 bug
./scripts/rebuild.sh
```

修复后的发布包在 `dist/*-patched.jar`。

---

## Workflow

1. Copy the original package to `original/` (for example `original/app.jar`).
2. Inspect layout, Java version, and entry point:

   ```bash
   ./scripts/inspect-jar.sh
   ```

3. Decompile with Vineflower (CFR output is kept under `work/decompiled-cfr` as a second opinion):

   ```bash
   ./scripts/decompile.sh
   ```

4. Edit sources under `src/main/java` to fix bugs. Note the files you change in `BUGS.md`.
5. Build the release package. Default mode **overlays** recompiled classes onto the original JAR, so a few decompiler failures do not block a patch:

   ```bash
   ./scripts/rebuild.sh
   ```

   Full Maven package from sources only:

   ```bash
   ./scripts/rebuild.sh --full
   ```

6. The patched JAR is written to `dist/<name>-patched.jar`.

## Layout

| Path | Purpose |
|------|---------|
| `original/` | Drop the vendor JAR here |
| `src/main/java` | Decompiled Java to edit |
| `src/main/resources` | Non-class resources copied from the JAR |
| `tools/` | Vineflower + CFR decompilers |
| `scripts/` | inspect / decompile / rebuild / selftest |
| `dist/` | Patched release JAR |
| `work/` | Intermediate extract/compile output (gitignored) |

## Requirements

- JDK 17+ (this environment has OpenJDK 21; `--release` still targets the original bytecode version)
- `unzip`, `zip`, `python3`
- Maven is only required for `./scripts/rebuild.sh --full`

## Verify the toolchain

```bash
./scripts/selftest.sh
```

That compiles a tiny buggy JAR, decompiles it, patches the bug, rebuilds, and checks the result.
