package com.bizzsoft.lipsync.mobilevsr

data class TranscriptContext(val previousText: String, val maxChars: Int = 4000)
data class RescoredTranscript(
    val text: String,
    val sourceHypothesisIndexes: List<Int>,
    val languageConfidence: Float
)

/**
 * Language models may select/recombine evidence but callers must retain source hypotheses.
 * Implementations must be configured for conservative decoding.
 */
interface EvidenceRescorer {
    suspend fun rescore(result: VsrResult, context: TranscriptContext): Result<RescoredTranscript>
}
