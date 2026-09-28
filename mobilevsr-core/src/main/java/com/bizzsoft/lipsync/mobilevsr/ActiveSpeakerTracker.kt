package com.bizzsoft.lipsync.mobilevsr
data class MouthMotion(val faceId:Long,val timestampUs:Long,val aperture:Float,val confidence:Float)
class ActiveSpeakerTracker(private val smoothing:Float=0.7f){
 private val energy=mutableMapOf<Long,Float>()
 fun update(m:MouthMotion):Long?{
  val prev=energy[m.faceId]?:0f
  val next=(smoothing*prev+(1f-smoothing)*m.aperture*m.confidence).coerceAtLeast(0f)
  energy[m.faceId]=next
  return energy.maxByOrNull{it.value}?.key
 }
 fun clear(){energy.clear()}
}
