package com.github.drewstephensdesigns.astrofeed.data.remote

import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.data.local.model.News
import com.github.drewstephensdesigns.astrofeed.data.local.model.Rocket
import com.github.drewstephensdesigns.astrofeed.data.local.model.SpaceX
import com.github.drewstephensdesigns.astrofeed.data.local.model.SubredditModel
import com.github.drewstephensdesigns.astrofeed.utils.Config
import com.github.drewstephensdesigns.astrofeed.utils.Config.SPACEX_UPCOMING_LAUNCHES
import com.github.drewstephensdesigns.astrofeed.utils.LaunchPaginatedResponse
import com.github.drewstephensdesigns.astrofeed.utils.NewsPaginatedResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceService {

    @GET(SPACEX_UPCOMING_LAUNCHES)
    suspend fun getUpcomingLaunches(
        @Query("limit") limit: Int = 50,
        @Query("search") search: String = "spacex",
        @Query("mode") mode: String = "detailed"
    ): Response<LaunchPaginatedResponse<LaunchResponse>>

    /*
    @GET(SPACEX_PAST_LAUNCHES)
    suspend fun getPastLaunches(
        @Query("limit") limit: Int = 50,
        @Query("search") search: String = "",
        @Query("mode") mode: String = "detailed"
    ): Response<LaunchPaginatedResponse<LaunchResponse>>
    */

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

    @GET(Config.REDDIT_SUBREDDIT)
    suspend fun getRedditFeed(
        @Path(Config.REDDIT_PARAM_SUBREDDIT) subreddit: String,
        @Path(Config.REDDIT_PARAM_ORDER) order: String = Config.REDDIT_PARAM_ORDER_HOT,
        @Query(Config.REDDIT_PARAM_LIMIT) limit: Int,
        @Query(Config.REDDIT_QUERY_AFTER) id: String? = null
    ) : Response<SubredditModel>
}
