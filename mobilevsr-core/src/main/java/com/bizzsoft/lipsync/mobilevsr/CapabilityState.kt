package com.bizzsoft.lipsync.mobilevsr

enum class CapabilityState { LOADING, READY, DEGRADED, UNSUPPORTED, ERROR }

data class DeviceCapability(
    val totalRamMb: Long,
    val cpuCores: Int,
    val androidApi: Int,
    val availableStorageMb: Long,
    val profile: VsrProfile,
    val state: CapabilityState,
    val reason: String? = null
)

object VsrProfileSelector {
    fun select(totalRamMb: Long, cpuCores: Int, availableStorageMb: Long): DeviceCapability {
        val state: CapabilityState
        val profile: VsrProfile
        val reason: String?
        when {
            totalRamMb < 2800 || cpuCores < 4 -> {
                state = CapabilityState.UNSUPPORTED; profile = VsrProfile.LITE
                reason = "Device does not meet the minimum local VSR capability."
            }
            totalRamMb < 4500 -> {
                state = CapabilityState.DEGRADED; profile = VsrProfile.LITE
                reason = "Lite visual-speech profile selected."
            }
            totalRamMb < 7500 -> {
                state = CapabilityState.READY; profile = VsrProfile.STANDARD; reason = null
            }
            else -> {
                state = CapabilityState.READY; profile = VsrProfile.PRO; reason = null
            }
        }
        return DeviceCapability(totalRamMb, cpuCores, android.os.Build.VERSION.SDK_INT,
            availableStorageMb, profile, state, reason)
    }
}
