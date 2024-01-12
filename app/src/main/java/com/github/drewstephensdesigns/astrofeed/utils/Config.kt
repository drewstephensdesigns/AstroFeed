package com.github.drewstephensdesigns.astrofeed.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.Toast
import com.github.drewstephensdesigns.astrofeed.R
import com.squareup.picasso.Picasso
import es.dmoral.toasty.Toasty
import java.math.RoundingMode
import java.text.DecimalFormat
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

    // Legacy Endpoints
    const val LAUNCHES = "v5/launches"
    const val ROCKETS = "v4/rockets"
    const val CAPSULES = "v4/capsules"

    // BASE URL
    const val SPACE_DEV_BASE_URL = "https://ll.thespacedevs.com/2.2.0/"
    const val MOCK_LAUNCH_LIBRARY_BASE_URL = "https://lldev.thespacedevs.com/2.2.0/"
    const val SPACEFLIGHT_NEWS_BASE_URL = "https://api.spaceflightnewsapi.net/v4/"

    // New Endpoints
    const val SPACEX_AGENCY = "agencies/121/"
    const val SPACEFLIGHT_NEWS_ARTICLES = "articles"
    const val SPACECRAFT = "spacecraft"


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
            .error(R.drawable.ic_starlink)
            .into(this)
    }

    fun getLocalTimeFromUnix(unixTime: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        simpleDateFormat.timeZone = Calendar.getInstance().timeZone
        return simpleDateFormat.format(Date(unixTime * 1000))
    }
}