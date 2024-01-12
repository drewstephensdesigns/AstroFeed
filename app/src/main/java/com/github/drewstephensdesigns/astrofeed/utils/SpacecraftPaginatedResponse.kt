package com.github.drewstephensdesigns.astrofeed.utils

import com.google.gson.annotations.SerializedName

class SpacecraftPaginatedResponse<T>(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<T>
)