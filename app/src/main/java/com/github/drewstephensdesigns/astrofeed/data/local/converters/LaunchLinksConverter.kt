package com.github.drewstephensdesigns.astrofeed.data.local.converters

import androidx.room.TypeConverter
import com.github.drewstephensdesigns.astrofeed.data.local.model.Launches
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LaunchLinksConverter {
    private val gson = Gson()
    private val type = object : TypeToken<Launches.Links>() {}.type

    @TypeConverter
    fun launchSiteToString(links: Launches.Links): String? = gson.toJson(links, type)

    @TypeConverter
    fun stringToLaunchSite(linksString: String): Launches.Links? = gson.fromJson(linksString, type)
}