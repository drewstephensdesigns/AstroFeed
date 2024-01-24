package com.github.drewstephensdesigns.astrofeed.data.pagination

import com.google.gson.annotations.SerializedName

data class NewsPaginatedResponse<T>(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String?,
    @SerializedName("previous") val previous: String?,
    @SerializedName("results") val results: List<T>
)