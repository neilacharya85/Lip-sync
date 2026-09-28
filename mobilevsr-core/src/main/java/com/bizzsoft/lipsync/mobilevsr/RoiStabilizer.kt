package com.bizzsoft.lipsync.mobilevsr

/** EMA stabilizer reduces mouth crop jitter without retaining image data. */
class RoiStabilizer(private val alpha: Float = 0.65f) {
    init { require(alpha in 0f..1f) }
    private var previous: NormalizedRect? = null

    fun update(current: NormalizedRect): NormalizedRect {
        val p = previous
        if (p == null) { previous = current; return current }
        fun mix(old: Float, fresh: Float) = alpha * fresh + (1f-alpha) * old
        return NormalizedRect(
            mix(p.left,current.left), mix(p.top,current.top),
            mix(p.right,current.right), mix(p.bottom,current.bottom)
        ).also { previous = it }
    }
    fun reset() { previous = null }
}
