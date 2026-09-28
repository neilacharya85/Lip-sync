package com.bizzsoft.lipsync.mobilevsr

/**
 * Fixed-capacity temporal buffer. When inference cannot keep up with capture,
 * oldest frames are discarded rather than allowing unbounded RAM growth.
 */
class TemporalFrameBuffer(private val capacity: Int) {
    init { require(capacity > 0) }
    private val frames = ArrayDeque<MouthFrame>(capacity)
    var droppedFrames: Long = 0
        private set

    @Synchronized fun offer(frame: MouthFrame) {
        if (frames.size == capacity) {
            frames.removeFirst()
            droppedFrames++
        }
        frames.addLast(frame)
    }

    @Synchronized fun snapshot(minFrames: Int = 1): MouthFrameSequence? {
        if (frames.size < minFrames) return null
        return MouthFrameSequence(frames.toList())
    }

    @Synchronized fun clear() = frames.clear()
    @Synchronized fun size(): Int = frames.size
}
