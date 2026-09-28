# Lip-sync / MobileVSR Architecture

## Goal
Offline-first Android visual speech recognition for 3 GB RAM devices upward.

## Pipeline
CameraX -> face landmarks -> stabilized mouth ROI -> bounded temporal buffer -> MobileVSR -> top-N evidence -> contextual language rescoring -> transcript.

## Non-negotiable engineering rules
1. No camera, vision, VSR, ASR or LLM inference on the main thread.
2. Bounded queues drop stale frames rather than accumulating RAM.
3. VSR exposes top-N hypotheses and confidence. Gemma must not erase the evidence trail.
4. Models and transcripts persist across APK upgrades.
5. Capability state is truthful: LOADING / READY / DEGRADED / UNSUPPORTED / ERROR.
6. Device profiling selects LITE / STANDARD / PRO; unsupported models are disabled.
7. Visual-only remains functional without microphone permission.
8. Raw video/mouth frames are ephemeral by default.
9. No hidden/background camera or microphone capture.
10. Model provenance, license, hash and version are recorded.

## Modules
- app: Compose UI, permissions, CameraX session, foreground session state.
- mobilevsr-core: model-agnostic inference contracts, temporal sequence types, hypotheses and diagnostics.
- future vision-preprocess: face tracking, landmarks, ROI stabilization.
- future model-runtime: LiteRT/ONNX implementations.
- future context-engine: Gemma evidence-constrained rescoring.
- future av-fusion: optional offline ASR and audiovisual fusion.

## v0.1 acceptance
Real CameraX input -> stable mouth ROI -> bounded temporal sequence -> MobileVSR API -> benchmark output for latency, FPS, peak RAM, dropped frames and thermal state.
