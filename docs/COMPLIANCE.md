# Privacy and Google Play engineering baseline

- Explicit user-initiated capture.
- Visible camera/microphone session state.
- CAMERA permission only when needed; RECORD_AUDIO optional for AV mode.
- No covert/background surveillance behavior.
- No raw frame/video retention by default.
- App remains useful in visual-only mode when microphone is denied.
- Prefer app-private storage and Storage Access Framework for user exports.
- Audit every SDK and model dependency before release.
- Maintain accurate privacy policy and Play Data Safety declarations.
- Provide transcript/model/cache deletion controls.
- Any analytics/crash reporting must be separately reviewed for data transmission.
- Revalidate current Google Play target API and policy requirements before each production release.

This file is an engineering checklist, not a substitute for release-time policy/legal review.
