package com.bizzsoft.lipsync.mobilevsr
data class AudioHypothesis(val text:String,val confidence:Float)
data class FusedHypothesis(val text:String,val confidence:Float,val visualEvidence:Boolean,val audioEvidence:Boolean)
object AudioVisualFusion {
 fun fuse(visual:List<VsrHypothesis>,audio:List<AudioHypothesis>,visualWeight:Float=0.6f):List<FusedHypothesis>{
  val scores=linkedMapOf<String,Triple<Float,Boolean,Boolean>>()
  visual.forEach{h-> val k=h.text.trim(); if(k.isNotEmpty()) scores[k]=Triple(h.confidence*visualWeight,true,false)}
  audio.forEach{h->
   val k=h.text.trim(); if(k.isEmpty())return@forEach
   val old=scores[k]
   scores[k]=Triple((old?.first?:0f)+h.confidence*(1f-visualWeight),old?.second?:false,true)
  }
  return scores.map{(t,v)->FusedHypothesis(t,v.first.coerceIn(0f,1f),v.second,v.third)}
   .sortedByDescending{it.confidence}
 }
}
