package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.drewstephensdesigns.astrofeed.data.local.model.Rocket
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RocketViewModel(private val app : Application): AndroidViewModel(app) {

    private var _rocketList = ArrayList<Rocket>()
    private val _rocketListObjects = MutableLiveData<List<Rocket>>()
    val rocketListObjects: MutableLiveData<List<Rocket>>
        get() = _rocketListObjects

    init {
        fetchRockets()
    }

    private fun fetchRockets(){
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO) {
                    service.getAllRockets()
                }

                val sortedList = response.sortedByDescending { it.id }
                _rocketList.clear()
                _rocketList.addAll(sortedList)

                _rocketListObjects.postValue(_rocketList)

            } catch (e: Exception){
                Config.errorToast(app.applicationContext, e)
            }
        }
    }
}