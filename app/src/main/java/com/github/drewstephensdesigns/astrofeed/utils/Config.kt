package com.github.drewstephensdesigns.astrofeed.utils

import android.content.Context
import android.widget.ImageView
import com.github.drewstephensdesigns.astrofeed.R
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Config {

    // API SOURCES
    // https://docs.spacexdata.com
    // https://github.com/r-spacex/SpaceX-API/tree/master/docs

    // Legacy API
    const val BASE_URL = "https://api.spacexdata.com/"

    const val ROCKETS = "v4/rockets"

    // BASE URL
    const val SPACE_DEV_BASE_URL = "https://ll.thespacedevs.com/2.2.0/"
    const val MOCK_LAUNCH_LIBRARY_BASE_URL = "https://lldev.thespacedevs.com/2.2.0/"
    const val SPACEFLIGHT_NEWS_BASE_URL = "https://api.spaceflightnewsapi.net/v4/"
    const val REDDIT_URL = "https://www.reddit.com/"

    // New Endpoints
    const val SPACEX_AGENCY = "agencies/121/"
    const val SPACEFLIGHT_NEWS_ARTICLES = "articles"
    const val SPACECRAFT = "spacecraft"

    // Reddit Endpoints
    const val REDDIT_PARAM_SUBREDDIT = "subreddit"
    const val REDDIT_PARAM_ORDER = "order"
    const val REDDIT_PARAM_LIMIT = "limit"

    const val REDDIT_SUBREDDIT =
        "r/{$REDDIT_PARAM_SUBREDDIT}/{$REDDIT_PARAM_ORDER}/.json?raw_json=1"

    const val REDDIT_PARAM_ORDER_HOT = "hot"
    const val REDDIT_PARAM_ORDER_NEW = "new"
    const val REDDIT_QUERY_AFTER = "after"

    // launches
    const val SPACEX_UPCOMING_LAUNCHES = "launch/upcoming"



    const val LAUNCH_LINKS = "links"
    const val LAUNCH_PATCH = "patch"
    const val LAUNCH_PATCH_SMALL = "small"
    const val LAUNCH_PATCH_LARGE = "large"
    const val LAUNCH_REDDIT = "reddit"
    const val LAUNCH_REDDIT_CAMPAIGN = "campaign"
    const val LAUNCH_REDDIT_LAUNCH = "launch"
    const val LAUNCH_REDDIT_MEDIA = "media"
    const val LAUNCH_REDDIT_RECOVERY = "recovery"
    const val LAUNCH_FLICKR = "flickr"
    const val LAUNCH_FLICKR_SMALL = "small"
    const val LAUNCH_FLICKR_ORIGINAL = "original"
    const val LAUNCH_WEBCAST = "webcast"
    const val LAUNCH_YOUTUBE_ID = "youtube_id"
    const val LAUNCH_ARTICLE = "article"
    const val LAUNCH_WIKI = "wikipedia"
    const val LAUNCH_AUTO_UPDATE = "auto_update"


    // Toasts
    fun successToast(context: Context, message: String) {
        Toasty.success(context, message, Toasty.LENGTH_SHORT, false).show()
    }

    fun errorToast(context: Context, e: Exception) {
        Toasty.error(context, e.toString(), Toasty.LENGTH_SHORT, false).show()
    }

    fun infoToast(context: Context, message: String) {
        Toasty.info(context, message, Toasty.LENGTH_SHORT, false).show()
    }

    fun warningToast(context: Context, message: String) {
        Toasty.warning(context, message, Toasty.LENGTH_SHORT).show()
    }

    fun normalToast(context: Context, message: String) {
        Toasty.warning(context, message, Toasty.LENGTH_SHORT).show()
    }

    //TODO("Change out error image)
    fun ImageView.loadImage(url : String?){
        Picasso.get().load(url)
            .fit()
            .placeholder(R.drawable.ic_galaxy)
            .error(R.drawable.ic_galaxy)
            .into(this)
    }

    fun getLocalTimeFromUnix(unixTime: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        simpleDateFormat.timeZone = Calendar.getInstance().timeZone
        return simpleDateFormat.format(Date(unixTime * 1000))
    }

}