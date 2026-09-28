package com.bizzsoft.lipsync.mobilevsr

/** Normalized coordinates [0,1]. The landmark implementation lives outside the engine. */
data class NormalizedPoint(val x: Float, val y: Float)
data class MouthLandmarks(
    val leftCorner: NormalizedPoint,
    val rightCorner: NormalizedPoint,
    val upperLip: NormalizedPoint,
    val lowerLip: NormalizedPoint,
    val trackingConfidence: Float
)
data class NormalizedRect(val left: Float, val top: Float, val right: Float, val bottom: Float)

object MouthRoiCalculator {
    fun calculate(m: MouthLandmarks, padding: Float = 0.45f): NormalizedRect {
        val width = (m.rightCorner.x - m.leftCorner.x).coerceAtLeast(0.01f)
        val height = (m.lowerLip.y - m.upperLip.y).coerceAtLeast(width * 0.25f)
        val cx = (m.leftCorner.x + m.rightCorner.x) / 2f
        val cy = (m.upperLip.y + m.lowerLip.y) / 2f
        val halfW = width * (0.5f + padding)
        val halfH = maxOf(height * (0.5f + padding), halfW * 0.55f)
        return NormalizedRect(
            (cx-halfW).coerceIn(0f,1f), (cy-halfH).coerceIn(0f,1f),
            (cx+halfW).coerceIn(0f,1f), (cy+halfH).coerceIn(0f,1f)
        )
    }
}
