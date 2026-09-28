# Model strategy

## VSR
Auto-AVSR is the teacher/reference family for accuracy benchmarking and distillation experiments. Its research checkpoints are not automatically distributable with this commercial Android app. Every checkpoint must pass the provenance registry before inclusion.

Production target: a distilled/quantized visual speech model derived from a commercially permissible training path, optimized for mouth tensors and Android acceleration. The runtime remains swappable through MobileVsrEngine.

Initial architecture target:
mouth sequence -> lightweight spatiotemporal frontend -> compact visual encoder -> small Conformer/temporal encoder -> CTC/token head -> top-N hypotheses.

The production model must be selected by measured WER/CER and Android resource performance, not parameter count alone.

## Context model
Preferred capable tier: Gemma 4 E2B quantized through LiteRT-LM when device capability permits.
Lower-memory devices must be able to run VSR without the language model. A smaller compatible rescorer may be selected after benchmarking.

The language model is not the lip reader. It receives hypotheses, confidences and bounded transcript context. It may rescore ambiguity but must not turn absent visual evidence into asserted speech.

## Future AV mode
Offline ASR and audiovisual fusion remain separate optional modules. Denying microphone permission must not break visual-only mode.
