plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "com.bizzsoft.lipsync"
    compileSdk = 36
    defaultConfig {
        applicationId = "com.bizzsoft.lipsync"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0-alpha01"
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
}
dependencies { implementation(project(":mobilevsr-core")) }
