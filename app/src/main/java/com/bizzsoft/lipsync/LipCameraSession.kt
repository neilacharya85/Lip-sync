package com.bizzsoft.lipsync

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

class LipCameraSession(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val analysisExecutor: Executor,
    private val previewView: PreviewView,
    private val onFrame: (ImageAnalysisFrame) -> Unit,
    private val onState: (CameraSessionState) -> Unit = {}
) {
    private var provider: ProcessCameraProvider? = null
    private var lensFacing = CameraSelector.LENS_FACING_BACK

    fun start() {
        onState(CameraSessionState.STARTING)
        val future = ProcessCameraProvider.getInstance(context)
        future.addListener({
            runCatching {
                val p = future.get()
                provider = p
                val preview = Preview.Builder().build().also {
                    it.surfaceProvider = previewView.surfaceProvider
                }
                val analysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                analysis.setAnalyzer(analysisExecutor) { image ->
                    try {
                        onFrame(ImageAnalysisFrame(image.imageInfo.timestamp, image.width, image.height))
                    } finally { image.close() }
                }
                p.unbindAll()
                p.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.Builder().requireLensFacing(lensFacing).build(),
                    preview,
                    analysis
                )
            }.onSuccess { onState(CameraSessionState.ACTIVE) }
             .onFailure { onState(CameraSessionState.ERROR) }
        }, analysisExecutor)
    }

    fun switchCamera() {
        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK)
            CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
        start()
    }

    fun stop() {
        provider?.unbindAll()
        provider = null
        onState(CameraSessionState.STOPPED)
    }
}
enum class CameraSessionState { STOPPED, STARTING, ACTIVE, ERROR }
data class ImageAnalysisFrame(val timestampNs: Long, val width: Int, val height: Int)
