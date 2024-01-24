package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.drewstephensdesigns.astrofeed.data.local.model.LaunchResponse
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PreviousLaunchesViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _previousLaunches = MutableLiveData<List<LaunchResponse>>()
    val previousLaunches: LiveData<List<LaunchResponse>> get() = _previousLaunches

    init {
        fetchLaunches()
    }
    private fun fetchLaunches(){
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.MOCK_LAUNCH_LIBRARY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO){
                    service.getPastLaunches()
                }

                val launches = response.body()?.results?.sortedBy { it.lastUpdated }
                _previousLaunches.postValue(launches!!)

            } catch (e: Exception){
                e.printStackTrace()
                Config.errorToast(app.applicationContext, e)
            }
        }
    }


}