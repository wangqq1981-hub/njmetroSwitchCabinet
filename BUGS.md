# Bug log

Source: `original/nj-metroSwitchCabinet.jar`  
Patched release: `dist/nj-metroSwitchCabinet-patched.jar`

| ID | Symptom | Root cause | File | Fix |
|----|---------|------------|------|-----|
| B1 | `/monitor/index/telemetryLast` 500 when a device has no telemetry row | `BeanUtils.copyProperties(null, dto)` NPE | `MonitorController` | Copy only when the row exists; otherwise return an empty DTO with `deviceCode` |
| B2 | `/monitor/index/remoteSignalingLast` 500 on missing data or null Integer fields | Map NPE, model NPE, `Integer == 1` unboxing NPE | `MonitorController` | Null-check live map/models/DB row; use `Integer.valueOf(1).equals(...)` |
| B3 | Monitor remaining operations can go negative | `20000 - close - open` with no floor | `MonitorController` | `Math.max(0, ...)` |
| B4 | `/alarmRecord/setAlarmRecordReadFinal` 500 for unknown id | `selectById` then dereference; parameter was `Integer` vs model `Long` | `AlarmRecordController` | Accept `Long`, return 400/404 instead of NPE |
| B5 | Circuit-breaker remaining life 500 or wrong % | Redis `multiGet` nulls / `Long` vs `Integer` cast; remaining can be negative | `CircuitBreakerServiceImpl` | `RemainingLifeUtil` Number-safe parse, clamp to 0 |
| B6 | `getEnergyStorageList` always `null` | Unimplemented stub | `CircuitBreakerServiceImpl` | Query latest N rows, never return null |
| B7 | Three-station history mixed 本体柜/隔离柜 | `getCaoList` ignored `deviceNum` (3 vs 4) | `ThreeStationsController` | Filter `dataType = deviceNum` like `getCaoDetail` |
| B8 | Startup NPE / stream leak reading classpath JSON | `readFile2Json` no null check, no try-with-resources | `ThreeStationsController` | Fail with a clear IOException; close streams |
| B9 | IEC 104 / hex helpers NPE on null buffers | `array.length` without null check; big-endian float used `> 4` instead of `!= 4` | `ByteArrayUtil` | Null-safe length checks |
| B10 | WebSocket close stack dumped to stdout | `e.printStackTrace()` | `MonitorViewSessionManager` | `log.error` |

IEC 104 listener NPE guards (`CountDownLatch` / `readSelectFile`) and `ConcurrentHashMap` for the live device map are in source but not overlaid into the patched JAR (private `j60870` / `SunTerminal` APIs are not on the compile classpath). They will ship in a full rebuild when those libraries are available.
