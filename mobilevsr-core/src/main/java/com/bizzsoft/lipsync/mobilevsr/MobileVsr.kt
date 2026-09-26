package com.bizzsoft.lipsync.mobilevsr

/** Evidence-first API: VSR preserves ambiguity; language models may rescore but not invent evidence. */
data class VsrHypothesis(val text: String, val confidence: Float)
data class VsrDiagnostics(
    val inferenceMs: Long,
    val droppedFrames: Long,
    val trackingConfidence: Float,
    val thermalStatus: Int
)
data class VsrResult(
    val hypotheses: List<VsrHypothesis>,
    val visualConfidence: Float,
    val diagnostics: VsrDiagnostics
)

interface MobileVsrEngine {
    suspend fun initialize(profile: VsrProfile): Result<Unit>
    suspend fun infer(sequence: MouthFrameSequence): Result<VsrResult>
    suspend fun close()
}

enum class VsrProfile { LITE, STANDARD, PRO }

data class MouthFrame(
    val timestampUs: Long,
    val width: Int,
    val height: Int,
    val luminance: ByteArray
)

data class MouthFrameSequence(val frames: List<MouthFrame>) {
    init { require(frames.isNotEmpty()) }
}
