package com.bizzsoft.lipsync.mobilevsr
data class TranscriptSegment(val id:String,val startUs:Long,val endUs:Long,val speakerId:Long?,val text:String,val confidence:Float,val alternatives:List<String>)
interface TranscriptStore {
 suspend fun append(segment:TranscriptSegment)
 suspend fun list():List<TranscriptSegment>
 suspend fun delete(id:String)
 suspend fun deleteAll()
}
