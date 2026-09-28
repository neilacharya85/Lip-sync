package com.bizzsoft.lipsync.mobilevsr
data class ConfidenceBreakdown(val visual:Float,val tracking:Float,val separation:Float,val final:Float)
object ConfidenceEngine{
 fun score(hypotheses:List<VsrHypothesis>,tracking:Float):ConfidenceBreakdown{
  val visual=hypotheses.firstOrNull()?.confidence?.coerceIn(0f,1f)?:0f
  val second=hypotheses.getOrNull(1)?.confidence?:0f
  val separation=(visual-second).coerceIn(0f,1f)
  val t=tracking.coerceIn(0f,1f)
  val final=(0.55f*visual+0.30f*t+0.15f*separation).coerceIn(0f,1f)
  return ConfidenceBreakdown(visual,t,separation,final)
 }
}
