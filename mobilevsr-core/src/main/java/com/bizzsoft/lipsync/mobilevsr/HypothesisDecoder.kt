package com.bizzsoft.lipsync.mobilevsr
interface TokenVocabulary{fun decode(tokenIds:IntArray):String}
class HypothesisDecoder(private val vocabulary:TokenVocabulary){
 fun decode(raw:List<RawTokenHypothesis>,topK:Int=5):List<VsrHypothesis>{
  if(raw.isEmpty())return emptyList()
  val selected=raw.sortedByDescending{it.logProbability}.take(topK)
  val maxLog=selected.maxOf{it.logProbability}
  val weights=selected.map{kotlin.math.exp((it.logProbability-maxLog).toDouble())}
  val denom=weights.sum().coerceAtLeast(1e-12)
  return selected.zip(weights).map{(h,w)->VsrHypothesis(vocabulary.decode(h.tokenIds),(w/denom).toFloat())}
 }
}
