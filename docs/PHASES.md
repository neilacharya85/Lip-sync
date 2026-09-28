# Sequential implementation phases

- Phase 0: build/CI, contracts, provenance and privacy baseline — in progress.
- Phase 1: CameraX capture, preview, permissions, bounded backpressure — in progress.
- Phase 2: face landmarks, active face selection, mouth ROI, stabilization and tensor preprocessing.
- Phase 3: VSR runtime adapter, model manifest/download integrity, top-N decoding and benchmark harness.
- Phase 4: Gemma evidence rescorer with bounded rolling context and conservative decoding.
- Phase 5: optional microphone ASR + audiovisual fusion.
- Phase 6: transcript persistence/export/delete controls and polished responsive UI.
- Phase 7: device matrix, thermal/battery/WER regression testing, Play/Data Safety release audit.

No phase is considered complete merely because UI is present; CI plus phase-specific functional metrics are required.
