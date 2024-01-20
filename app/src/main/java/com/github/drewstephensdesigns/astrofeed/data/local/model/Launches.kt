package com.github.drewstephensdesigns.astrofeed.data.local.model

import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_ARTICLE
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_PATCH
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_PATCH_LARGE
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_PATCH_SMALL
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_REDDIT
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_REDDIT_CAMPAIGN
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_REDDIT_LAUNCH
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_REDDIT_MEDIA
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_REDDIT_RECOVERY
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_WEBCAST
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_WIKI
import com.github.drewstephensdesigns.astrofeed.utils.Config.LAUNCH_YOUTUBE_ID
import com.google.gson.annotations.SerializedName

data class LaunchResponse(
    @SerializedName("id") val id: String,
    @SerializedName("url") val url: String?,
    @SerializedName("slug") val slug: String?,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: Status?,
    @SerializedName("last_updated") val lastUpdated: String?,
    @SerializedName("net") val net: String?,
    @SerializedName("window_end") val windowEnd: String?,
    @SerializedName("window_start") val windowStart: String?,
    @SerializedName("probability") val probability: Int?,
    @SerializedName("holdreason") val holdReason: String?,
    @SerializedName("failreason") val failReason: String?,
    @SerializedName("hashtag") val hashtag: String?,
    @SerializedName("launch_service_provider") val launchServiceProvider: LaunchServiceProvider?,
    @SerializedName("rocket") val rocket: Rocket?,
    @SerializedName("mission") val mission: Mission?,
    @SerializedName("pad") val pad: Pad?,
    @SerializedName("vidURLs") val video: List<Video>?,
    @SerializedName("webcast_live") val webcastLive: Boolean?,
    @SerializedName("image") val image: String?,
    @SerializedName("infographic") val infographic: String?,
    @SerializedName("program") val program: List<Program>?,
    @SerializedName("orbital_launch_attempt_count") val orbitalLaunchAttemptCount: Int?,
    @SerializedName("location_launch_attempt_count") val locationLaunchAttemptCount: Int?,
    @SerializedName("pad_launch_attempt_count") val padLaunchAttemptCount: Int?,
    @SerializedName("agency_launch_attempt_count") val agencyLaunchAttemptCount: Int?,
    @SerializedName("orbital_launch_attempt_count_year") val orbitalLaunchAttemptCountYear: Int?,
    @SerializedName("location_launch_attempt_count_year") val locationLaunchAttemptCountYear: Int?,
    @SerializedName("pad_launch_attempt_count_year") val padLaunchAttemptCountYear: Int?,
    @SerializedName("agency_launch_attempt_count_year") val agencyLaunchAttemptCountYear: Int?,
    @SerializedName("mission_patches") val missionPatches: List<MissionPatch>?,
) {

    data class Status(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("abbrev") val abbrev: String?,
        @SerializedName("description") val description: String?
    )

    data class LaunchServiceProvider(
        @SerializedName("id") val id: Int,
        @SerializedName("url") val url: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("type") val type: String?
    )

    data class Rocket(
        @SerializedName("id") val id: Int,
        @SerializedName("configuration") val configuration: Configuration?,
        @SerializedName("launcher_stage") val launcherStage: List<LauncherStage?>?,
        @SerializedName("spacecraft_stage") val spacecraftStage: SpacecraftStage?
    ) {

        data class Configuration(
            @SerializedName("id") val id: Int,
            @SerializedName("url") val url: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("family") val family: String?,
            @SerializedName("full_name") val fullName: String?,
            @SerializedName("variant") val variant: String?
        )

        data class LauncherStage(
            @SerializedName("id") val id: Int,
            @SerializedName("type") val type: String?,
            @SerializedName("reused") val reused: Boolean?,
            @SerializedName("launcher") val launcher: Launcher?,
            @SerializedName("landing") val landing: Landing?,
            @SerializedName("turn_around_time_days") val turnAroundTimeDays: Int?,
            @SerializedName("previous_flight") val previousFlight: PreviousFlight?
        ) {

            data class Launcher(
                @SerializedName("id") val id: Int,
                @SerializedName("serial_number") val serialNumber: String?,
                @SerializedName("status") val status: String?,
                @SerializedName("flights") val flights: Int?,
            )

            data class Landing(
                @SerializedName("id") val id: Int,
                @SerializedName("attempt") val attempt: Boolean?,
                @SerializedName("success") val success: Boolean?,
                @SerializedName("description") val description: String?,
                @SerializedName("location") val location: Location?,
                @SerializedName("type") val type: Type?,
            ) {

                data class Location(
                    @SerializedName("id") val id: Int,
                    @SerializedName("name") val name: String?,
                    @SerializedName("abbrev") val abbrev: String?,
                    @SerializedName("description") val description: String?,
                    @SerializedName("successful_landings") val successfulLandings: Int?,
                )

                data class Type(
                    @SerializedName("id") val id: Int,
                    @SerializedName("name") val name: String?,
                    @SerializedName("abbrev") val abbrev: String?,
                    @SerializedName("description") val description: String?
                )
            }

            data class PreviousFlight(
                @SerializedName("id") val id: String,
                @SerializedName("name") val name: String?
            )
        }

        data class SpacecraftStage(
            @SerializedName("id") val id: Int,
            @SerializedName("destination") val destination: String?,
            @SerializedName("launch_crew") val launchCrew: List<LaunchCrew?>?
        ) {

            data class LaunchCrew(
                @SerializedName("id") val id: Int,
                @SerializedName("role") val role: Role?,
                @SerializedName("astronaut") val astronaut: Astronaut?,
            ) {

                data class Role(
                    @SerializedName("id") val id: Int,
                    @SerializedName("role") val role: String?,
                    @SerializedName("priority") val priority: Int?
                )

                data class Astronaut(
                    @SerializedName("id") val id: Int,
                    @SerializedName("name") val name: String?,
                    @SerializedName("status") val status: Status?,
                    @SerializedName("agency") val agency: Agency?,
                    @SerializedName("bio") val bio: String?,
                    @SerializedName("profile_image") val profileImage: String?,
                    @SerializedName("last_flight") val lastFlight: String?,
                    @SerializedName("first_flight") val firstFlight: String?
                ) {

                    data class Status(
                        @SerializedName("id") val id: Int,
                        @SerializedName("name") val name: String?,
                    )

                    data class Agency(
                        @SerializedName("id") val id: Int,
                        @SerializedName("name") val name: String?
                    )
                }
            }
        }
    }

    data class Mission(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("launch_designator") val launchDesignator: String?,
        @SerializedName("type") val type: String?,
        @SerializedName("orbit") val orbit: Orbit?
    ) {

        data class Orbit(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String?,
            @SerializedName("abbrev") val abbrev: String?
        )
    }

    data class Pad(
        @SerializedName("id") val id: Int,
        @SerializedName("url") val url: String?,
        @SerializedName("agency_id") val agencyId: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("info_url") val infoUrl: String?,
        @SerializedName("wiki_url") val wikiUrl: String?,
        @SerializedName("map_url") val mapUrl: String?,
        @SerializedName("latitude") val latitude: String?,
        @SerializedName("longitude") val longitude: String?,
        @SerializedName("location") val location: Location,
        @SerializedName("map_image") val mapImage: String?,
        @SerializedName("total_launch_count") val totalLaunchCount: Int?,
        @SerializedName("orbital_launch_attempt_count") val orbitalLaunchAttemptCount: Int?,
    ) {

        data class Location(
            @SerializedName("id") val id: Int,
            @SerializedName("url") val url: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("country_code") val countryCode: String?,
            @SerializedName("map_image") val mapImage: String?,
            @SerializedName("total_launch_count") val totalLaunchCount: Int?,
            @SerializedName("total_landing_count") val totalLandingCount: Int?
        )
    }

    data class Video(
        @SerializedName("priority") val priority: Int,
        @SerializedName("source") val source: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("feature_image") val featureImage: String?,
        @SerializedName("url") val url: String,
        @SerializedName("type") val type: Type?,
    ) {

        data class Type(
            @SerializedName("id") val id: Int,
            @SerializedName("name") val name: String,
        )
    }

    data class Program(
        @SerializedName("id") val id: Int,
        @SerializedName("url") val url: String?,
        @SerializedName("name") val name: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("agencies") val agencies: List<Agency>?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("start_date") val startDate: String?,
        @SerializedName("end_date") val endDate: String?,
        @SerializedName("info_url") val infoUrl: String?,
        @SerializedName("wiki_url") val wikiUrl: String?,
        @SerializedName("mission_patches") val missionPatches: List<MissionPatch>?
    ) {

        data class Agency(
            @SerializedName("id") val id: Int,
            @SerializedName("url") val url: String?,
            @SerializedName("name") val name: String?,
            @SerializedName("type") val type: String?
        )
    }

    data class MissionPatch(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String?,
        @SerializedName("priority") val priority: Int?,
        @SerializedName("image_url") val imageUrl: String?,
        @SerializedName("agency") val agency: Program.Agency?,
    )
}

data class LaunchLinks(
    @SerializedName(LAUNCH_PATCH) val missionPatch: MissionPatch? = null,
    @SerializedName(LAUNCH_REDDIT) val redditLinks: MissionRedditLinks? = null,
    @SerializedName(LAUNCH_WEBCAST) val webcast: String? = null,
    @SerializedName(LAUNCH_YOUTUBE_ID) val youtubeId: String? = null,
    @SerializedName(LAUNCH_ARTICLE) val article: String? = null,
    @SerializedName(LAUNCH_WIKI) val wikipedia: String? = null
)

data class MissionPatch(
    @SerializedName(LAUNCH_PATCH_SMALL) val patchSmall: String?,
    @SerializedName(LAUNCH_PATCH_LARGE) val patchLarge: String?
)

data class MissionRedditLinks(
    @SerializedName(LAUNCH_REDDIT_CAMPAIGN) val campaign: String?,
    @SerializedName(LAUNCH_REDDIT_LAUNCH) val launch: String?,
    @SerializedName(LAUNCH_REDDIT_MEDIA) val media: String?,
    @SerializedName(LAUNCH_REDDIT_RECOVERY) val recovery: String?
)