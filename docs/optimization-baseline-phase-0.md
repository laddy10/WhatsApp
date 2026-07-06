# Optimization Baseline - Phase 0

## Scope

- Branch: `codex/optimizacion-framework-fase-0`
- Base commit: `568b13c2915789f9cb01106c0158ba581ed14e49`
- Date: 2026-07-02
- Execution mode: local, sequential, one Gradle process
- Device: Xiaomi 22101316UG, Android 12
- Java: 11.0.13
- ADB: 35.0.2
- Appium: 1.22.3

## Control Scenarios

| Tag | Coverage |
| --- | --- |
| `@Whatsapp_Post_02` | Postpaid conversational response |
| `@Whatsapp_Post_07` | Extended postpaid navigation and external destination |
| `@Whatsapp_10` | Prepaid recharge and payment redirection |
| `@Whatsapp_19` | Prepaid interactive list |
| `@EXUS_HOG_WSP_06` | Basic home-account navigation without OTP |

## Local Run 1

- Gradle duration: 25 min 18 sec
- Scenarios: 5
- Successful: 3
- Failed: 2
- Screenshots: 357
- Serenity report size: 90,061,652 bytes (about 85.9 MiB)

| Tag | Result | Scenario duration | Failed step |
| --- | --- | ---: | --- |
| `@Whatsapp_Post_02` | SUCCESS | 235.219 sec | - |
| `@Whatsapp_Post_07` | SUCCESS | 315.712 sec | - |
| `@Whatsapp_10` | ERROR | 306.965 sec | Validar direccionamiento al medio de pago |
| `@Whatsapp_19` | SUCCESS | 302.205 sec | - |
| `@EXUS_HOG_WSP_06` | ERROR | 177.589 sec | Seleccionar menu principal hogar |

## Initial Findings

1. A single five-scenario run generated 357 screenshots and an 85.9 MiB report.
2. Compilation was shared, but every scenario repeated app-version and data-policy navigation.
3. `@Whatsapp_10` failed waiting for one of two Nequi responses.
4. `@EXUS_HOG_WSP_06` failed waiting for the expected home-account menu text.
5. The scenario following a failed scenario recovered the WhatsApp conversation and continued.

## Local Run 2

- Gradle duration: 23 min 56 sec
- Scenarios: 5
- Successful: 4
- Failed: 1
- Screenshots: 467
- Serenity report size: 103,098,996 bytes (98.32 MiB)

| Tag | Result | Scenario duration | Failed step |
| --- | --- | ---: | --- |
| `@Whatsapp_Post_02` | SUCCESS | 214.000 sec | - |
| `@Whatsapp_Post_07` | SUCCESS | 307.000 sec | - |
| `@Whatsapp_10` | SUCCESS | 349.574 sec | - |
| `@Whatsapp_19` | SUCCESS | 202.586 sec | - |
| `@EXUS_HOG_WSP_06` | ERROR | 170.975 sec | Seleccionar menu principal hogar |

The postpaid and home durations above come from the JUnit XML timestamps. The metrics exporter only retained the two prepaid scenario totals, which is itself an observability defect.

## Local Comparison

| Metric | Run 1 | Run 2 | Difference |
| --- | ---: | ---: | ---: |
| Gradle duration | 25 min 18 sec | 23 min 56 sec | -1 min 22 sec (-5.4%) |
| Successful scenarios | 3/5 | 4/5 | +1 |
| Screenshots | 357 | 467 | +110 (+30.8%) |
| Report size | 85.9 MiB | 98.32 MiB | +12.42 MiB (+14.5%) |

## Consolidated Findings

1. `@EXUS_HOG_WSP_06` is reproducibly unstable: both runs failed at `Seleccionar menu principal hogar`, waiting 20 seconds for one of the expected menu texts.
2. `@Whatsapp_10` is intermittent: it failed on payment redirection in run 1 and passed in run 2.
3. The other three control scenarios passed in both runs.
4. Total runtime varied by 5.4%, but screenshot count varied by 30.8%; evidence generation is excessive and not proportional to scenario count.
5. The run-2 metrics JSON omitted three scenario totals even though JUnit and Serenity reported all five scenarios.
6. The test process recovered after the home failure and executed both subsequent postpaid scenarios successfully.

## Pending Measurements

- Two equivalent runs in the remote server environment.
- Comparison of scenario duration, pass rate, screenshot count, and report size.

The local baseline is complete. Phase 1 can be implemented and validated locally with the same control set; remote baseline runs remain necessary before evaluating server-side impact.
## Phase 1 - Local Validation

Changes under test:

- Appium capabilities and hub are read from `serenity.properties`, with JVM `-D` overrides for remote execution.
- The APK capability is omitted when `appium.app` is empty.
- The driver implicit wait now respects `webdriver.timeouts.implicitlywait=3000` instead of forcing 20 seconds.
- Serenity screenshots use `AFTER_EACH_STEP` instead of `FOR_EACH_ACTION`.
- The explicit Serenity screenshot duplicated by `EvidenciaUtils` was removed; the existing manual evidence remains.

Validation run 1:

- Gradle duration: 18 min 36 sec
- Scenarios: 5
- Successful: 3
- Failed: 2
- Screenshots: 360
- Serenity report size: 78,361,180 bytes (74.73 MiB)

| Tag | Result | Scenario duration | Observation |
| --- | --- | ---: | --- |
| `@Whatsapp_Post_02` | SUCCESS | 188.986 sec | Stable across all three local runs |
| `@Whatsapp_Post_07` | SUCCESS | 257.763 sec | Stable across all three local runs |
| `@Whatsapp_10` | ERROR | 207.662 sec | Known intermittent response: `Medios de pago` |
| `@Whatsapp_19` | SUCCESS | 221.790 sec | Stable across all three local runs |
| `@EXUS_HOG_WSP_06` | ERROR | 149.063 sec | Same reproducible home-menu text failure |

Comparison against local baseline run 2:

| Metric | Baseline run 2 | Phase 1 | Difference |
| --- | ---: | ---: | ---: |
| Gradle duration | 23 min 56 sec | 18 min 36 sec | -5 min 20 sec (-22.3%) |
| Screenshots | 467 | 360 | -107 (-22.9%) |
| Report size | 98.32 MiB | 74.73 MiB | -23.59 MiB (-24.0%) |

The lower implicit wait did not introduce a new failure category. Driver sessions were created successfully without sending an APK path, and the stable control scenarios remained successful.
Validation run 2:

- Gradle duration: 18 min 59 sec
- Scenarios: 5
- Successful: 4
- Failed: 1
- Screenshots: 377
- Serenity report size: 81,486,983 bytes (77.71 MiB)
- Only `@EXUS_HOG_WSP_06` failed, at the same home-menu validation.

Two-run averages:

| Metric | Baseline average | Phase 1 average | Difference |
| --- | ---: | ---: | ---: |
| Gradle duration | 24 min 37 sec | 18 min 48 sec | -5 min 50 sec (-23.7%) |
| Screenshots | 412.0 | 368.5 | -43.5 (-10.6%) |
| Report size | 92.11 MiB | 76.22 MiB | -15.89 MiB (-17.3%) |

Phase 1 acceptance: approved locally. Both runs created Appium sessions correctly, all three stable scenarios passed twice, `@Whatsapp_10` preserved its known intermittent pattern, and `@EXUS_HOG_WSP_06` preserved its known reproducible failure.
## Phase 2 - Stability And Wait Analysis

Implemented changes:

- Home-account selection now resolves the option using the configured last four digits instead of sending the fixed option `1`.
- Missing configured lines are classified as `[TEST_DATA]`.
- Recharge processing failures are classified as `[CHANNEL_UNAVAILABLE]`.
- The transition from `Continuar recarga` to `Medios de pago` retries only when the button is still available.
- The metrics listener is reattached without duplication for each scenario/event bus.

Validated findings:

- The metrics export contains all 5 scenario totals; the previous result contained only the 2 prepaid scenarios.
- The current Claro menu does not contain the configured home account `2961`.
- The menu observed on 2026-07-02 contained 13 postpaid and 7 prepaid accounts, but no home-account section.
- The old fixed value `1` selected postpaid line `8443`; therefore the previous home test was a false positive navigation.
- During recharge validation Claro returned: `no fue posible procesar tu solicitud, vuelve a intentar mas tarde`.
- The experimental zero-implicit-wait polling and automatic failure recovery were rejected after a 0/5 control result and removed from the accepted implementation.

Current status:

- Phase 2 is partially validated.
- Java compilation passed before the final early channel-classification adjustment.
- The final Gradle launcher attempts were blocked before a Java process started; a final compile and focused rerun remain pending.
- Home validation requires a valid home account to be associated with the test user or an updated `numeroHogar`.
### Phase 2 Reduced Control Set

At the user's request, `@EXUS_HOG_WSP_06` was removed from the optimization control set without deleting its feature.

Current control tags:

- `@Whatsapp_10`
- `@Whatsapp_19`
- `@Whatsapp_Post_02`
- `@Whatsapp_Post_07`

Valid Gradle run:

- Duration: 15 min 39 sec
- Scenarios: 4
- Successful: 2
- Failed: 2
- Metrics totals exported: 4/4
- `@Whatsapp_Post_02`: SUCCESS, 179.177 sec
- `@Whatsapp_Post_07`: SUCCESS, 258.762 sec
- `@Whatsapp_10`: ERROR, 195.337 sec
- `@Whatsapp_19`: ERROR, 225.155 sec

Corrections after this run:

- The recharge transition wait was restored from the experimental 10 seconds to its original 20-second contract. The failure page source already contained `Medios de pago` immediately after the 10-second timeout.
- The shared `Vaciar chat` step now reactivates `com.whatsapp` before opening the overflow menu. `@Whatsapp_19` had reached an external authentication screen before cleanup.
- Main and test source changes compile successfully. The focused Gradle rerun remains pending because the elevated wrapper blocked before starting Java.
- A direct JUnit execution was discarded as test evidence because bypassing Gradle changed dependency resolution and activated an incompatible OkHttp version in the Ollama listener.
### Phase 2 Focused Prepago Validation

Execution method:

- Gradle wrapper executed through `cmd.exe` because the PowerShell launch layer blocked before creating Java processes.
- Runner tag was temporarily set to `@Whatsapp_10,@Whatsapp_19` and restored to `@Whatsapp_Post_10` immediately after execution.

Result:

- Gradle result: BUILD SUCCESSFUL
- Duration: 9 min 33 sec
- Scenarios: 2
- Successful: 2
- Failed: 0
- Metrics totals exported: 2/2
- `@Whatsapp_10`: SUCCESS, 304.735 sec
- `@Whatsapp_19`: SUCCESS, 192.903 sec

Phase 2 reduced-scope acceptance: approved locally. The two postpaid controls passed in the preceding valid four-scenario run, and both corrected prepaid controls passed in the focused rerun. Home-account scenarios remain outside the optimization control set.
## Phase 4 - Explicit Wait Optimization

Scope:

- Ollama and its retry/backoff behavior were explicitly excluded.
- Only fixed waits used by the four prepaid/postpaid control scenarios were changed.

Changes:

- Removed the fixed 5-second pause after prepaid data-policy authorization; the existing response wait now controls progress.
- Removed both fixed 1-second pauses from line selection.
- Removed the fixed 10-second pause before locating the voice-package `Selecciona` button; the existing polling interaction controls progress.
- Removed the fixed 4-second pause before the postpaid loyalty response.
- Replaced the fixed 90-second Claro Club pause with a conditional wait for Android to leave `com.whatsapp`, bounded at 20 seconds.
- Removed the fixed 3-second pause before validating payment-link text already covered by a response wait.

Validation:

- Gradle result: BUILD SUCCESSFUL
- Duration: 16 min 7 sec
- Scenarios: 4
- Successful: 4
- Failed: 0
- Metrics totals exported: 4/4

| Tag | Phase 4 duration | Previous successful duration | Difference |
| --- | ---: | ---: | ---: |
| `@Whatsapp_10` | 313.494 sec | 304.735 sec | +8.759 sec |
| `@Whatsapp_19` | 194.179 sec | 192.903 sec | +1.276 sec |
| `@Whatsapp_Post_02` | 186.066 sec | 179.177 sec | +6.889 sec |
| `@Whatsapp_Post_07` | 185.029 sec | 258.762 sec | -73.733 sec |

The combined scenario duration decreased from 935.577 to 878.768 seconds: 56.809 seconds less (-6.1%). The three small increases are within observed channel/device variability; the deterministic improvement is the removal of the 90-second Claro Club pause.

Phase 4 acceptance: approved locally.
