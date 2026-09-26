package com.bizzsoft.lipsync

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

class MainActivity : Activity(), LifecycleOwner {
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private lateinit var preview: PreviewView
    private lateinit var status: TextView
    private lateinit var startStop: Button
    private var session: LipCameraSession? = null
    private var active = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER_HORIZONTAL
        }
        status = TextView(this).apply { text = "Visual Only • Camera stopped"; textSize = 18f; setPadding(24,24,24,24) }
        preview = PreviewView(this).apply { layoutParams = LinearLayout.LayoutParams(-1,0,1f) }
        startStop = Button(this).apply { text = "Start lip reading"; setOnClickListener { toggleCamera() } }
        val switch = Button(this).apply { text = "Switch camera"; setOnClickListener { session?.switchCamera() } }
        root.addView(status); root.addView(preview); root.addView(startStop); root.addView(switch)
        setContentView(root)
    }

    private fun toggleCamera() {
        if (active) { session?.stop(); active=false; startStop.text="Start lip reading"; return }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
        } else startCamera()
    }

    private fun startCamera() {
        session?.stop()
        session = LipCameraSession(this, this, cameraExecutor, preview, onFrame = { _ -> }) { state ->
            runOnUiThread {
                active = state == CameraSessionState.ACTIVE
                status.text = "Visual Only • " + when(state) {
                    CameraSessionState.ACTIVE -> "Camera active"
                    CameraSessionState.STARTING -> "Starting camera"
                    CameraSessionState.ERROR -> "Camera error"
                    CameraSessionState.STOPPED -> "Camera stopped"
                }
                startStop.text = if(active) "Stop lip reading" else "Start lip reading"
            }
        }
        session?.start()
    }

    override fun onRequestPermissionsResult(requestCode:Int, permissions:Array<out String>, grantResults:IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==CAMERA_REQUEST && grantResults.firstOrNull()==PackageManager.PERMISSION_GRANTED) startCamera()
        else status.text="Visual Only • Camera permission required"
    }

    override fun onDestroy() { session?.stop(); cameraExecutor.shutdown(); super.onDestroy() }
    companion object { private const val CAMERA_REQUEST=1001 }
}
