# MobileVSR engine ownership boundary

## Owned by this project
- temporal mouth-frame representation and buffering
- ROI stabilization policy
- tensor normalization contract
- VSR model architecture/training/distillation path
- token/hypothesis decoding
- confidence scoring
- active-speaker motion logic
- device-tier model selection
- model manifest/integrity/provenance system
- audiovisual fusion policy
- evidence-constrained language-model orchestration
- benchmarks and acceptance thresholds

## Replaceable infrastructure
CameraX provides Android camera access. A landmark library may provide generic facial coordinates. LiteRT/ONNX may execute tensors. Gemma may provide contextual language inference. None of those components defines the lip-reading engine.

Third-party pretrained speech-recognition weights are not rebranded as MobileVSR. Any teacher/reference model is recorded as such, and production weights require a documented training/distillation and licensing path.
