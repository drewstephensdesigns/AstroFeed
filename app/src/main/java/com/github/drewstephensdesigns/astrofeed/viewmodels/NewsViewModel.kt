package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.drewstephensdesigns.astrofeed.data.local.model.News
import com.github.drewstephensdesigns.astrofeed.data.pagination.NewsPaginatedResponse
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NewsViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _newsList = ArrayList<News>()
    private val _newsListObjects = MutableLiveData<List<News>>()
    val newsListObjects: LiveData<List<News>> get() = _newsListObjects

    private val viewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    init {
        fetchNews()
    }

    private fun fetchNews() {
        viewModelScope.launch {
            try {
                val service = createSpaceService()

                val response = withContext(Dispatchers.IO) {
                    service.getArticles(100, 0)
                }

                handleApiResponse(response)

            } catch (e: Exception) {
                handleApiError(e)
            }
        }
    }

    private fun createSpaceService(): SpaceService {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Config.SPACEFLIGHT_NEWS_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(SpaceService::class.java)
    }

    private fun handleApiResponse(response: NewsPaginatedResponse<News>) {
        val sortedList = response.results.sortedByDescending { it.publishedAt }

        _newsList.clear()
        _newsList.addAll(sortedList)

        _newsListObjects.postValue(_newsList)
    }

    private fun handleApiError(e: Exception) {
        Config.errorToast(app.applicationContext, e)
        Log.e("ERROR", e.toString())
        // Handle error state, e.g., post an empty list or show an error message
        _newsListObjects.postValue(emptyList())
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}