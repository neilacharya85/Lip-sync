plugins { id("com.android.application"); id("org.jetbrains.kotlin.android") }
android {
    namespace = "com.bizzsoft.lipsync"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.bizzsoft.lipsync"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "0.1.0-alpha01"
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
    kotlinOptions { jvmTarget = "17" }
}
dependencies { implementation(project(":mobilevsr-core")) }
