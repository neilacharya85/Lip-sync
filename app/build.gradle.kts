plugins { id("com.android.application") }
android {
    namespace = "com.bizzsoft.lipsync"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.bizzsoft.lipsync"
        minSdk = 26
        targetSdk = 36
        versionCode = 2
        versionName = "0.1.0-alpha02"
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
}
dependencies {
    implementation(project(":mobilevsr-core"))
    implementation("androidx.camera:camera-core:1.5.0")
    implementation("androidx.camera:camera-camera2:1.5.0")
    implementation("androidx.camera:camera-lifecycle:1.5.0")
    implementation("androidx.camera:camera-view:1.5.0")
    implementation("androidx.lifecycle:lifecycle-runtime:2.9.3")
}
