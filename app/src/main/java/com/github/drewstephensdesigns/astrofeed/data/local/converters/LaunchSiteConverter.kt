package com.github.drewstephensdesigns.astrofeed.data.local.converters

import androidx.room.TypeConverter
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchSite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LaunchSiteConverter {
    private val gson = Gson()
    private val type = object : TypeToken<LaunchSite>() {}.type

    @TypeConverter
    fun launchSiteToString(nextLaunchSite: LaunchSite): String? = gson.toJson(nextLaunchSite, type)

    @TypeConverter
    fun stringToLaunchSite(nextLaunchSiteString: String): LaunchSite? = gson.fromJson(nextLaunchSiteString, type)
}