package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.drewstephensdesigns.astrofeed.data.local.model.RedditPost
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RedditViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _redditListObjects = MutableLiveData<List<RedditPost>>()
    val redditListObjects: LiveData<List<RedditPost>> get() = _redditListObjects

    init {
        fetchRedditPosts()
    }

    private fun fetchRedditPosts(){
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.REDDIT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO) {
                    service.getRedditFeed(
                        "space+spacex+spaceflight",
                        order =  Config.REDDIT_PARAM_ORDER_HOT,
                        limit = 150,
                        id = Config.REDDIT_QUERY_AFTER,
                    )
                }

                val redditPosts = response.body()?.data?.children?.map {
                    RedditPost(it.data)
                } ?: emptyList()

                _redditListObjects.postValue(redditPosts)

            }catch (e: Exception){
                Config.errorToast(app.applicationContext, e)
            }
        }
    }
}