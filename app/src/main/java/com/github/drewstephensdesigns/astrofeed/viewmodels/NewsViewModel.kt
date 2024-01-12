package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.drewstephensdesigns.astrofeed.data.local.model.News
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsViewModel(private val app: Application) : AndroidViewModel(app) {

    private var _newsList = ArrayList<News>()
    private val _newsListObjects = MutableLiveData<List<News>>()
    val newsListObjects: MutableLiveData<List<News>>
        get() = _newsListObjects

    init {
        fetchNews()
    }

    private fun fetchNews(){
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.SPACEFLIGHT_NEWS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO){
                    service.getArticles(60, 0)
                }

                val sortedList = response.results.sortedByDescending { it.id }

                _newsList.clear()
                _newsList.addAll(sortedList)

                _newsListObjects.postValue(_newsList)

            } catch (e: Exception){
                Config.errorToast(app.applicationContext, e)
                Log.e("ERROR", e.toString())
            }
        }
    }
}