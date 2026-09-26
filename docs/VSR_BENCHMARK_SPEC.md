# MobileVSR benchmark specification

Every candidate runtime/model must be measured on-device. Do not use desktop throughput as the Android acceptance criterion.

## Metrics
- visual WER and CER on labelled clips
- end-to-end latency (p50 / p95)
- effective mouth-frame FPS
- dropped-frame count/rate
- peak PSS/RSS memory
- model load time and on-disk size
- thermal status and throttling over 10/30 minute sessions
- tracking confidence and failure rate
- battery consumption for a 30 minute session

## Scenario matrix
Frontal / ~15° / ~30° head pose; indoor bright / indoor dim / outdoor; near / medium distance; slow / normal / fast speech; clean background / multiple visible faces.

## Device tiers
LITE: 3–4 GB RAM reference.
STANDARD: 4–8 GB.
PRO: 8 GB+.

A model is not promoted merely because its transcript looks plausible. Evidence metrics and resource limits must pass.
