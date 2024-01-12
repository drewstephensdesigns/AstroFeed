package com.github.drewstephensdesigns.astrofeed.data.remote

import com.github.drewstephensdesigns.astrofeed.data.local.model.Capsules
import com.github.drewstephensdesigns.astrofeed.data.local.model.Launches
import com.github.drewstephensdesigns.astrofeed.data.local.model.News
import com.github.drewstephensdesigns.astrofeed.utils.NewsPaginatedResponse
import com.github.drewstephensdesigns.astrofeed.data.local.model.Rocket
import com.github.drewstephensdesigns.astrofeed.data.local.model.SpaceX
import com.github.drewstephensdesigns.astrofeed.data.local.model.Spacecraft
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.utils.SpacecraftPaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SpaceService {

    @GET(Config.LAUNCHES)
    suspend fun getAllLaunches() : Response<List<Launches>>

    @GET(Config.ROCKETS)
    suspend fun getAllRockets() : List<Rocket>

    @GET(Config.SPACEX_AGENCY)
    suspend fun getCompanyInfo(): Response<SpaceX>

    @GET(Config.SPACEFLIGHT_NEWS_ARTICLES)
    suspend fun getArticles(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("title") search: String = "Space"
    ) : NewsPaginatedResponse<News>

    @GET(Config.SPACECRAFT)
    suspend fun getSpaceCraft(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("in_space") in_space: Boolean,
        @Query("search") search: String
    ) : SpacecraftPaginatedResponse<Spacecraft>
}