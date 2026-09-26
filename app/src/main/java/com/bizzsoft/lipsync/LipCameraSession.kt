package com.bizzsoft.lipsync

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

/**
 * Camera session is lifecycle-bound and keeps only the latest frame.
 * Frames are never persisted here.
 */
class LipCameraSession(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val analysisExecutor: Executor,
    private val onFrame: (ImageAnalysisFrame) -> Unit
) {
    private var provider: ProcessCameraProvider? = null

    fun start() {
        val future = ProcessCameraProvider.getInstance(context)
        future.addListener({
            val p = future.get()
            provider = p
            val analysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            analysis.setAnalyzer(analysisExecutor) { image ->
                try {
                    onFrame(ImageAnalysisFrame(image.imageInfo.timestamp, image.width, image.height))
                } finally {
                    image.close()
                }
            }
            p.unbindAll()
            p.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, analysis)
        }, analysisExecutor)
    }

    fun stop() {
        provider?.unbindAll()
        provider = null
    }
}

data class ImageAnalysisFrame(val timestampNs: Long, val width: Int, val height: Int)
