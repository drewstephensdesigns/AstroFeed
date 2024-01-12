package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.google.gson.annotations.SerializedName

data class SpaceX(
    @SerializedName("id"                              ) var id                            : Int?     = null,
    @SerializedName("url"                             ) var url                           : String?  = null,
    @SerializedName("name"                            ) var name                          : String?  = null,
    @SerializedName("featured"                        ) var featured                      : Boolean? = null,
    @SerializedName("type"                            ) var type                          : String?  = null,
    @SerializedName("country_code"                    ) var countryCode                   : String?  = null,
    @SerializedName("abbrev"                          ) var abbrev                        : String?  = null,
    @SerializedName("description"                     ) var description                   : String?  = null,
    @SerializedName("administrator"                   ) var administrator                 : String?  = null,
    @SerializedName("founding_year"                   ) var foundingYear                  : String?  = null,
    @SerializedName("launchers"                       ) var launchers                     : String?  = null,
    @SerializedName("spacecraft"                      ) var spacecraft                    : String?  = null,
    @SerializedName("parent"                          ) var parent                        : String?  = null,
    @SerializedName("launch_library_url"              ) var launchLibraryUrl              : String?  = null,
    @SerializedName("total_launch_count"              ) var totalLaunchCount              : Int?     = null,
    @SerializedName("successful_launches"             ) var successfulLaunches            : Int?     = null,
    @SerializedName("consecutive_successful_launches" ) var consecutiveSuccessfulLaunches : Int?     = null,
    @SerializedName("failed_launches"                 ) var failedLaunches                : Int?     = null,
    @SerializedName("pending_launches"                ) var pendingLaunches               : Int?     = null,
    @SerializedName("successful_landings"             ) var successfulLandings            : Int?     = null,
    @SerializedName("failed_landings"                 ) var failedLandings                : Int?     = null,
    @SerializedName("attempted_landings"              ) var attemptedLandings             : Int?     = null,
    @SerializedName("consecutive_successful_landings" ) var consecutiveSuccessfulLandings : Int?     = null,
    @SerializedName("info_url"                        ) var infoUrl                       : String?  = null,
    @SerializedName("wiki_url"                        ) var wikiUrl                       : String?  = null,
    @SerializedName("logo_url"                        ) var logoUrl                       : String?  = null,
    @SerializedName("image_url"                       ) var imageUrl                      : String?  = null,
    @SerializedName("nation_url"                      ) var nationUrl                     : String?  = null,
)