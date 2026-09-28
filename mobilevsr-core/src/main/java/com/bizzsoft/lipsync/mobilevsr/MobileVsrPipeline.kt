package com.bizzsoft.lipsync.mobilevsr
class MobileVsrPipeline(
 private val runtime:VsrNeuralRuntime,
 private val decoder:HypothesisDecoder
){
 suspend fun infer(sequence:VsrTensorSequence,trackingConfidence:Float,droppedFrames:Long,thermalStatus:Int):Result<VsrResult>{
  val start=System.nanoTime()
  return runtime.infer(sequence).map{raw->
   val decoded=decoder.decode(raw)
   val confidence=ConfidenceEngine.score(decoded,trackingConfidence)
   VsrResult(decoded,confidence.final,VsrDiagnostics(
    inferenceMs=(System.nanoTime()-start)/1_000_000,
    droppedFrames=droppedFrames,
    trackingConfidence=trackingConfidence,
    thermalStatus=thermalStatus
   ))
  }
 }
}
