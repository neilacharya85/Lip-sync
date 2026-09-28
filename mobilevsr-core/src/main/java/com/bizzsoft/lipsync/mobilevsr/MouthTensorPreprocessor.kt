package com.bizzsoft.lipsync.mobilevsr
data class MouthTensor(val timestampUs:Long,val width:Int,val height:Int,val values:FloatArray,val trackingConfidence:Float)
class MouthTensorPreprocessor(private val targetWidth:Int=112,private val targetHeight:Int=112){
 fun normalize(frame:MouthFrame,trackingConfidence:Float):MouthTensor{
  require(frame.width==targetWidth&&frame.height==targetHeight){"Unexpected mouth frame size"}
  require(frame.luminance.size==targetWidth*targetHeight)
  val out=FloatArray(frame.luminance.size)
  frame.luminance.forEachIndexed{i,b->out[i]=((b.toInt() and 0xff)/127.5f)-1f}
  return MouthTensor(frame.timestampUs,targetWidth,targetHeight,out,trackingConfidence.coerceIn(0f,1f))
 }
}
