package com.bizzsoft.lipsync.mobilevsr
data class FaceLandmarkFrame(val timestampUs:Long,val faceId:Long,val points:List<NormalizedPoint>,val confidence:Float)
interface MouthLandmarkAdapter { fun extract(frame:FaceLandmarkFrame):MouthLandmarks? }
class IndexedMouthLandmarkAdapter(private val left:Int,private val right:Int,private val upper:Int,private val lower:Int,private val minimumConfidence:Float=0.55f):MouthLandmarkAdapter {
 override fun extract(frame:FaceLandmarkFrame):MouthLandmarks? {
  if(frame.confidence<minimumConfidence)return null
  val max=maxOf(left,right,upper,lower)
  if(max>=frame.points.size||minOf(left,right,upper,lower)<0)return null
  return MouthLandmarks(frame.points[left],frame.points[right],frame.points[upper],frame.points[lower],frame.confidence)
 }
}
