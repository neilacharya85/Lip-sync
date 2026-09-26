package com.bizzsoft.lipsync.mobilevsr

class ModelRegistry(private val manifests: List<ModelManifest>) {
    private val byId = manifests.associateBy { it.id }
    init { require(byId.size == manifests.size) { "Duplicate model IDs" } }

    fun get(id: String): ModelManifest? = byId[id]

    fun compatible(profile: VsrProfile): List<ModelManifest> =
        manifests.filter { profile.ordinal >= it.minimumProfile.ordinal }
}
