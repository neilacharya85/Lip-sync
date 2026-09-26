package com.bizzsoft.lipsync.mobilevsr

data class ModelManifest(
    val id: String,
    val version: String,
    val sha256: String,
    val byteSize: Long,
    val minimumProfile: VsrProfile,
    val format: String,
    val licenseId: String
) {
    init {
        require(id.isNotBlank() && version.isNotBlank())
        require(sha256.matches(Regex("[a-fA-F0-9]{64}")))
        require(byteSize > 0)
        require(licenseId.isNotBlank())
    }
}

sealed interface ModelInstallState {
    data object Missing : ModelInstallState
    data class Downloading(val bytes: Long, val total: Long) : ModelInstallState
    data object Verifying : ModelInstallState
    data class Ready(val path: String) : ModelInstallState
    data class Failed(val reason: String) : ModelInstallState
}
