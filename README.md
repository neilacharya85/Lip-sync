# Lip-sync

## Android large-download notification requirement — 2026-09-27
When the Android implementation adds downloadable VSR/ASR/AI model packs or other large user-initiated assets, the download flow must follow the shared app standard:
- request Android 13+ notification permission contextually before the transfer begins;
- run long transfers as foreground/data-sync work rather than an Activity-only coroutine;
- show an ongoing Android notification with a live progress bar and percentage;
- let the notification return the user to the app;
- keep the transfer explicitly user initiated, resumable where practical, and integrity verified before activation;
- declare only the foreground-service permissions actually used and keep Google Play foreground-service declarations/demo videos aligned with the shipped behavior.

The repository currently contains no Android application source on `main`, so this is a required implementation gate for the future Android download path rather than a claim that the feature is already present.
