package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.google.gson.annotations.SerializedName

data class Rocket(
    @SerializedName("flickr_images"    ) var flickrImages   : ArrayList<String>         = arrayListOf(),
    @SerializedName("name"             ) var name           : String?                   = null,
    @SerializedName("type"             ) var type           : String?                   = null,
    @SerializedName("active"           ) var active         : Boolean?                  = null,
    @SerializedName("stages"           ) var stages         : Int?                      = null,
    @SerializedName("boosters"         ) var boosters       : Int?                      = null,
    @SerializedName("cost_per_launch"  ) var costPerLaunch  : Int?                      = null,
    @SerializedName("success_rate_pct" ) var successRatePct : Int?                      = null,
    @SerializedName("first_flight"     ) var firstFlight    : String?                   = null,
    @SerializedName("country"          ) var country        : String?                   = null,
    @SerializedName("company"          ) var company        : String?                   = null,
    @SerializedName("wikipedia"        ) var wikipedia      : String?                   = null,
    @SerializedName("description"      ) var description    : String?                   = null,
    @SerializedName("id"               ) var id             : String?                   = null,
    @SerializedName("engines"          ) var engines        : Engines?                  = Engines()

){
    data class Engines(
        @SerializedName("isp"              ) var isp            : Isp?            = Isp(),
        @SerializedName("thrust_sea_level" ) var thrustSeaLevel : ThrustSeaLevel? = ThrustSeaLevel(),
        @SerializedName("thrust_vacuum"    ) var thrustVacuum   : ThrustVacuum?   = ThrustVacuum(),
        @SerializedName("number"           ) var number         : Int?            = null,
        @SerializedName("type"             ) var type           : String?         = null,
        @SerializedName("version"          ) var version        : String?         = null,
        @SerializedName("layout"           ) var layout         : String?         = null,
        @SerializedName("engine_loss_max"  ) var engineLossMax  : Int?            = null,
        @SerializedName("propellant_1"     ) var propellant1    : String?         = null,
        @SerializedName("propellant_2"     ) var propellant2    : String?         = null,
        @SerializedName("thrust_to_weight" ) var thrustToWeight : Double?         = null
    ){
        data class Isp (
            @SerializedName("sea_level" ) var seaLevel : Int? = null,
            @SerializedName("vacuum"    ) var vacuum   : Int? = null
        )

        data class ThrustSeaLevel (
            @SerializedName("kN"  ) var kN  : Int? = null,
            @SerializedName("lbf" ) var lbf : Int? = null
        )

        data class ThrustVacuum (
            @SerializedName("kN"  ) var kN  : Int? = null,
            @SerializedName("lbf" ) var lbf : Int? = null
        )
    }
}
