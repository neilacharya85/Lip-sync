package com.bizzsoft.lipsync.mobilevsr

/**
 * Maintains a bounded, approximately frame-rate-normalized temporal mouth sequence.
 * It rejects frames arriving too soon and resets after long discontinuities.
 */
class TemporalTensorAssembler(
    private val targetFps:Int=25,
    private val windowFrames:Int=50,
    private val resetGapUs:Long=500_000L
){
    private val frames=ArrayDeque<MouthTensor>()
    private var lastAcceptedUs:Long?=null
    private val minimumSpacingUs=(1_000_000L/targetFps)

    @Synchronized fun offer(frame:MouthTensor):Boolean{
        val last=lastAcceptedUs
        if(last!=null){
            val gap=frame.timestampUs-last
            if(gap<0){ clear(); return false }
            if(gap>resetGapUs) clear()
            else if(gap<minimumSpacingUs) return false
        }
        frames.addLast(frame)
        lastAcceptedUs=frame.timestampUs
        while(frames.size>windowFrames) frames.removeFirst()
        return true
    }

    @Synchronized fun ready(minFrames:Int=windowFrames/2)=frames.size>=minFrames
    @Synchronized fun snapshot()=if(frames.isEmpty()) null else VsrTensorSequence(frames.toList())
    @Synchronized fun clear(){frames.clear();lastAcceptedUs=null}
    @Synchronized fun size()=frames.size
}
