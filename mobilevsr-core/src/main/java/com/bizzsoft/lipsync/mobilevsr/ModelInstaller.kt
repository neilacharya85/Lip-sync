package com.bizzsoft.lipsync.mobilevsr
import java.io.File
import java.io.InputStream
class ModelInstaller(private val verifier:Sha256Verifier=Sha256Verifier()){
 fun install(manifest:ModelManifest,input:InputStream,directory:File):Result<File> = runCatching {
  directory.mkdirs()
  val temp=File(directory,".${manifest.id}-${manifest.version}.part")
  val target=File(directory,"${manifest.id}-${manifest.version}.model")
  input.use{src->temp.outputStream().use{dst->src.copyTo(dst)}}
  require(temp.length()==manifest.byteSize){"Model size mismatch"}
  require(verifier.verify(temp,manifest.sha256)){"Model SHA-256 mismatch"}
  if(target.exists()) target.delete()
  require(temp.renameTo(target)){"Atomic model activation failed"}
  target
 }
}
