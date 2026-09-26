# Lip-sync / MobileVSR — Implementation History

This is the canonical engineering history for the Lip-sync Android app and MobileVSR engine.

## Maintenance rules
- Update this file on every meaningful development iteration/build.
- Never rewrite old entries to make the history look cleaner; corrections are appended and linked to the superseded decision.
- Record implementation, fixes, optimizations, removals/supersessions, validation status, known issues and next potential steps.
- Record build/version, branch/commit where practical, and CI/device validation state.
- A feature is not marked working merely because code exists; distinguish IMPLEMENTED, CI-VALIDATED, DEVICE-VALIDATED and BENCHMARK-VALIDATED.
- If this file approaches 5 MB, archive it as IMPLEMENTATION_HISTORY_PART_01.md and continue sequentially without a line-count limit.

---

## 2026-09-26 — MobileVSR v0.1 bootstrap / alpha01–alpha02

### Implemented
- Created Android project baseline targeting Android 16 / API 36.
- Created model-agnostic `MobileVsrEngine` contract.
- Added VSR hypotheses, visual confidence and diagnostics structures.
- Added fixed-capacity `TemporalFrameBuffer` with stale-frame dropping and dropped-frame accounting.
- Added device capability model with LITE / STANDARD / PRO profiles.
- Added truthful capability states: LOADING / READY / DEGRADED / UNSUPPORTED / ERROR.
- Added normalized mouth-landmark and ROI contracts.
- Added EMA-based mouth ROI stabilization.
- Added lifecycle-bound CameraX analysis session using KEEP_ONLY_LATEST backpressure.
- Added provider-neutral facial-landmark adapter.
- Added 112x112 luminance mouth tensor normalization.
- Added swappable `VsrNeuralRuntime` abstraction.
- Added top-K hypothesis decoder.
- Added MobileVSR confidence engine.
- Added active-speaker mouth-motion tracker.
- Added `MobileVsrPipeline` orchestration from neural output through decoding/confidence/diagnostics.
- Added evidence-constrained language-model rescoring contract.
- Added model manifests, compatibility registry and SHA-256 verification.
- Added VSR benchmark specification.
- Added model provenance and engine-ownership documentation.
- Added GitHub Actions Android build workflow with short APK artifact retention.

### Fixed
- Initial CI failed because AGP 9.1 built-in Kotlin support conflicted with the separately applied Kotlin Android plugin.
- Removed the redundant Kotlin Android plugin configuration and moved to the AGP 9 built-in Kotlin path.
- Corrected the project baseline to API 36.

### Optimized
- Camera analysis uses KEEP_ONLY_LATEST to prevent frame backlog.
- Temporal VSR buffering is bounded so inference lag cannot grow RAM indefinitely.
- VSR runtime is isolated behind an interface so CPU/GPU/NPU implementations and neural models can be replaced without rewriting the engine.
- Landmark provider numbering is isolated outside MobileVSR.
- Language-model correction is separated from VSR evidence generation so contextual models cannot silently become the lip reader.
- Device tiers allow heavier models only where hardware permits.

### Removed / superseded
- Superseded the original AGP + external Kotlin-plugin configuration with AGP 9 built-in Kotlin support.
- Superseded the idea of directly embedding a large research VSR checkpoint as the production engine.
- Auto-AVSR is retained as a teacher/reference family; production MobileVSR weights require a documented commercially permissible training/distillation path.

### Model strategy
- Auto-AVSR: high-capability VSR teacher/reference and benchmark source, subject to model/data licensing.
- Production: our Android-oriented MobileVSR architecture/weights, distilled/quantized through a documented permissible path.
- Gemma 4 E2B: preferred capable-device contextual rescorer, not the VSR engine.
- Lower-memory devices must retain visual VSR functionality without requiring Gemma.
- Optional offline ASR/AV fusion will be a later independent module.

### Validation status
- Base corrected Android project: CI-VALIDATED.
- CameraX/engine additions: CI validation occurs per branch commit.
- Real camera landmark pipeline: NOT YET DEVICE-VALIDATED.
- Production VSR neural weights: NOT YET IMPLEMENTED.
- Gemma contextual runtime: CONTRACT IMPLEMENTED; runtime NOT YET IMPLEMENTED.
- AV fusion: NOT YET IMPLEMENTED.
- WER/CER/thermal/battery benchmarks: SPEC IMPLEMENTED; measurements pending real model/device runs.

### Known issues / open engineering work
- Camera preview and runtime permission UX still need to be connected to the analysis session.
- Concrete face-landmark provider adapter and mouth crop/warp implementation are pending.
- Temporal tensor window policy requires empirical tuning.
- No production MobileVSR neural model has yet passed Android WER/resource benchmarks.
- No Gemma runtime/model download integration yet.
- Multi-speaker motion tracking is algorithmically present but not device validated.
- Need test corpus and reproducible labelled visual-speech benchmark inputs.

### Next potential steps
1. Finish camera preview and explicit permission/session UI.
2. Integrate concrete face landmarks and map provider indices into the neutral adapter.
3. Implement mouth crop, geometric alignment, resize and temporal tensor assembly.
4. Add unit tests for ROI, stabilization, buffers, decoding and confidence.
5. Implement first LiteRT/ONNX MobileVSR neural runtime.
6. Establish teacher-model evaluation and legally clean training/distillation pipeline.
7. Benchmark VSR candidate on WER/CER, latency, RAM, FPS and thermals.
8. Integrate persistent/resumable model installer with SHA-256 and atomic activation.
9. Integrate Gemma 4 E2B evidence-constrained rescoring on capable devices.
10. Add optional offline ASR and audiovisual fusion.
11. Add transcript persistence/export/delete controls and responsive production UI.
12. Run device-tier regression matrix and Google Play/Data Safety release audit.

---

## Entry template for future iterations

### YYYY-MM-DD — version/build
#### Implemented
#### Fixed
#### Optimized
#### Removed / superseded
#### Validation status
#### Known issues
#### Next potential steps


## 2026-09-26 — GitHub Actions artifact storage control

### Implemented
- APK artifacts now use unique run-numbered names.
- CI cleanup keeps only the latest five non-expired Lip-sync debug APK artifacts.
- Workflow receives narrowly scoped Actions write permission so it can delete its own obsolete artifacts.
- APK artifacts also retain the existing five-day expiry as a second storage-control layer.

### Optimized
- Artifact cleanup is count-based as well as age-based, preventing rapid iterative builds from accumulating many APK ZIPs during the five-day retention window.
- Source code, Git history and engineering-history Markdown are not part of artifact cleanup.

### Validation status
- Workflow configuration committed; next push/build validates cleanup execution under GitHub Actions permissions.

### Next potential steps
- Monitor artifact storage after several successful builds.
- If release/AAB artifacts are introduced, give them a separate retention policy so production deliverables are not treated as disposable debug APKs.


## 2026-09-26 — Build archive policy changed to Google Drive

### Implemented
- Created a dedicated Google Drive folder: Lip-sync MobileVSR Build Archive.
- New desired lifecycle: GitHub build -> retain latest five convenient artifacts -> archive older successful APKs to Drive -> verify archived file -> delete corresponding GitHub artifact.
- Build archive should retain version/build number, commit SHA and build date in its naming/metadata.

### Fixed
- Disabled the previously added destructive latest-five cleanup because GitHub Actions does not automatically inherit the interactive Google Drive connector authentication.
- GitHub artifact retention temporarily increased from five to thirty days while verified Drive archival is being established, reducing the risk of losing builds.

### Validation status
- Drive archive folder: CREATED.
- GitHub automatic deletion: DISABLED pending archive verification.
- Fully unattended GitHub-to-Drive archival: NOT YET AUTHENTICATED.

### Next potential steps
- Establish a GitHub Actions-compatible Google Drive credential/authorization mechanism without committing credentials to source.
- Upload an APK artifact to the Drive archive and verify size/hash/readback.
- Only after successful verification, enable archive-then-delete cleanup while retaining the latest five artifacts on GitHub.
