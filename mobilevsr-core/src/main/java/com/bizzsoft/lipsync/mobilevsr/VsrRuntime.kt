package com.bizzsoft.lipsync.mobilevsr
data class RuntimeCapabilities(val cpu:Boolean=true,val gpu:Boolean,val npu:Boolean)
data class VsrTensorSequence(val frames:List<MouthTensor>){init{require(frames.isNotEmpty())}}
data class RawTokenHypothesis(val tokenIds:IntArray,val logProbability:Float,val frameStart:Int,val frameEnd:Int)
interface VsrNeuralRuntime{
 suspend fun load(manifest:ModelManifest,modelPath:String):Result<Unit>
 suspend fun infer(sequence:VsrTensorSequence):Result<List<RawTokenHypothesis>>
 fun capabilities():RuntimeCapabilities
 suspend fun close()
}
