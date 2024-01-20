package com.github.drewstephensdesigns.astrofeed.utils

import com.google.gson.annotations.SerializedName

data class LaunchPaginatedResponse<T>(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<T>,
    @SerializedName("detail") val detail: String?
)