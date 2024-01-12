package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.google.gson.annotations.SerializedName

data class Capsules(

    @SerializedName("reuse_count"    ) var reuseCount    : Int?              = null,
    @SerializedName("water_landings" ) var waterLandings : Int?              = null,
    @SerializedName("land_landings"  ) var landLandings  : Int?              = null,
    @SerializedName("last_update"    ) var lastUpdate    : String?           = null,
    @SerializedName("launches"       ) var launches      : ArrayList<String> = arrayListOf(),
    @SerializedName("serial"         ) var serial        : String?           = null,
    @SerializedName("status"         ) var status        : String?           = null,
    @SerializedName("type"           ) var type          : String?           = null,
    @SerializedName("id"             ) var id            : String?           = null
)