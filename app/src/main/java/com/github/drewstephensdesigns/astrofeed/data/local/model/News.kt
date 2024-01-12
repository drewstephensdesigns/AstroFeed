package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.google.gson.annotations.SerializedName

data class News(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("title")
    var title: String? = null,

    @SerializedName("image_url")
    var image_url: String? = null,

    @SerializedName("url")
    var url: String? = null,

    @SerializedName("summary")
    var summary: String? = null,

    @SerializedName("published_at")
    var publishedAt: String? = null,

    @SerializedName("updated_at")
    var updatedAt: String? = null,
)