package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.google.gson.annotations.SerializedName

data class SubredditModel(
    @SerializedName("data") var data: SubredditDataModel
)

data class SubredditDataModel(
    @SerializedName("children") var children: List<SubredditPostModel>
)

data class SubredditPostModel(
    @SerializedName("data") var data: RedditPostData
)

data class RedditPostData(
    @SerializedName("id") var id: String,
    @SerializedName("selftext_html") var textHtml: String?,
    @SerializedName("title") var title: String,
    @SerializedName("name") var name: String,
    @SerializedName("author") var author: String,
    @SerializedName("created_utc") var created: Float,
    @SerializedName("thumbnail") var thumbnail: String,
    @SerializedName("score") var score: Int,
    @SerializedName("num_comments") var commentsCount: Int,
    @SerializedName("preview") var preview: RedditPreviewListModel?,
    @SerializedName("domain") var domain: String,
    @SerializedName("stickied") var stickied: Boolean,
    @SerializedName("is_self") var isSelf: Boolean,
    @SerializedName("is_reddit_media_domain") var redditDomain: Boolean,
    @SerializedName("permalink") var permalink: String,
    @SerializedName("subreddit") var subreddit: String
)

data class RedditPreviewListModel(
    @SerializedName("images") var images: List<RedditMediaAlternatesModel>
)

data class RedditMediaAlternatesModel(
    @SerializedName("source") var source: RedditMediaModel,
    @SerializedName("resolutions") var resolutions: List<RedditMediaModel>
)

data class RedditMediaModel(
    @SerializedName("url") var url: String,
    @SerializedName("width") var width: Int,
    @SerializedName("height") var height: Int
)

data class RedditPost(

    @SerializedName("id")
    var id: String,

    @SerializedName("name")
    var name: String,

    @SerializedName("title")
    var title: String,

    @SerializedName("description")
    var description: String?,

    @SerializedName("author")
    var author: String,

    @SerializedName("created")
    var created: Float,

    @SerializedName("thumbnail")
    var thumbnail: String,

    @SerializedName("score")
    var score: Int,

    @SerializedName("commentsCount")
    var commentsCount: Int,

    @SerializedName("preview")
    var preview: RedditPreviewListModel?,

    @SerializedName("domain")
    var domain: String,

    @SerializedName("stickied")
    var stickied: Boolean,

    @SerializedName("isSelf")
    var isSelf: Boolean,

    @SerializedName("redditDomain")
    var redditDomain: Boolean,

    @SerializedName("permalink")
    var permalink: String,

    @SerializedName("subreddit")
    var subreddit: String
){
    constructor(data: RedditPostData) : this(
        id = data.id,
        name = data.name,
        title = data.title,
        description = data.textHtml,
        author = data.author,
        created = data.created,
        thumbnail = data.thumbnail,
        score = data.score,
        commentsCount = data.commentsCount,
        preview = data.preview,
        domain = data.domain,
        stickied = data.stickied,
        isSelf = data.isSelf,
        redditDomain = data.redditDomain,
        permalink = data.permalink,
        subreddit = data.subreddit
    )
}