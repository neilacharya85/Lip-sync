# Project log

## 2026-09-26 — MobileVSR v0.1 / alpha02
- Repository bootstrap and Android 16/API 36 baseline.
- Corrected AGP 9 built-in Kotlin configuration after CI exposed duplicate Kotlin extension.
- CI green after correction.
- Added model-agnostic MobileVSR evidence API.
- Added bounded temporal frame buffer and dropped-frame accounting.
- Added device capability tiers and truthful capability state.
- Added CameraX lifecycle analysis with KEEP_ONLY_LATEST backpressure.
- Added mouth ROI calculation and EMA ROI stabilization.
- Added VSR benchmark and model strategy documents.
- Selected Auto-AVSR as teacher/reference, subject to checkpoint/dataset licensing; production model will be mobile-distilled/quantized.
- Selected Gemma 4 E2B as preferred capable-device contextual rescorer, subject to on-device benchmarks.
- Added evidence-rescorer contract so LM output remains traceable to VSR hypotheses.
- Added model manifest, SHA-256 verification and compatibility registry foundations.
