package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.google.gson.annotations.SerializedName

data class Spacecraft(
    @SerializedName("id"                 ) var id               : Int?              = null,
    @SerializedName("url"                ) var url              : String?           = null,
    @SerializedName("name"               ) var name             : String?           = null,
    @SerializedName("serial_number"      ) var serialNumber     : String?           = null,
    @SerializedName("is_placeholder"     ) var isPlaceholder    : Boolean?          = null,
    @SerializedName("in_space"           ) var inSpace          : Boolean?          = null,
    @SerializedName("time_in_space"      ) var timeInSpace      : String?           = null,
    @SerializedName("time_docked"        ) var timeDocked       : String?           = null,
    @SerializedName("flights_count"      ) var flightsCount     : Int?              = null,
    @SerializedName("mission_ends_count" ) var missionEndsCount : Int?              = null,
    @SerializedName("status"             ) var status           : Status?           = Status(),
    @SerializedName("description"        ) var description      : String?           = null,
    @SerializedName("spacecraft_config"  ) var spacecraftConfig : SpacecraftConfig? = SpacecraftConfig()
) {
    data class Status (

        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null

    )
    data class Type (

        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("name" ) var name : String? = null

    )
    data class Agency (

        @SerializedName("id"   ) var id   : Int?    = null,
        @SerializedName("url"  ) var url  : String? = null,
        @SerializedName("name" ) var name : String? = null,
        @SerializedName("type" ) var type : String? = null

    )
    data class SpacecraftConfig (

        @SerializedName("id"        ) var id       : Int?     = null,
        @SerializedName("url"       ) var url      : String?  = null,
        @SerializedName("name"      ) var name     : String?  = null,
        @SerializedName("type"      ) var type     : Type?    = Type(),
        @SerializedName("agency"    ) var agency   : Agency?  = Agency(),
        @SerializedName("in_use"    ) var inUse    : Boolean? = null,
        @SerializedName("image_url" ) var imageUrl : String?  = null

    ) data class SpacecraftProfile(

        //HEIGHT	8.1 m / 26.7 ft
        @SerializedName("height") val height: Float?,

        // DIAMETER	4 m / 13 ft
        @SerializedName("diameter") val diameter: Float?,

        // TRUNK VOLUME	37 m³ / 1300 ft³
        @SerializedName("trunk_volume") val trunkVolume: Float?,

        // CAPSULE VOLUME	9.3 m³ / 328 ft³
        @SerializedName("capsule_volume") val capsuleVolume: Float?,

        // LAUNCH PAYLOAD MASS	6,000 kg / 13,228 lbs
        @SerializedName("payload_capacity") val payloadCapacity: Int?
    )
}