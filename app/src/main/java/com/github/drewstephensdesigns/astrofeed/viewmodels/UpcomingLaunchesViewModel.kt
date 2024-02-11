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
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class UpcomingLaunchesViewModel(private val app: Application) : AndroidViewModel(app) {

    private val _upcomingLaunches = MutableLiveData<List<LaunchResponse>>()
    val upcomingLaunches: LiveData<List<LaunchResponse>> get() = _upcomingLaunches

    init {
        fetchLaunches()
    }

    private fun fetchLaunches() {
        viewModelScope.launch {
            try {
                val client = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.SPACE_DEV_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO) {
                    service.getUpcomingLaunches()
                }

                if (isActive) { // Check if the coroutine is still active
                    val launches = response.body()?.results?.sortedBy { it.windowStart }
                    _upcomingLaunches.postValue(launches!!)
                }

            } catch (e: TimeoutException) {
                handleTimeoutError(e)
            } catch (e: Exception) {
                handleGenericError(e)
            }
        }
    }

    private fun handleTimeoutError(e: Exception) {
        // Handle timeout error (e.g., show a message to the user)
        Config.errorToast(app.applicationContext, e)
    }

    private fun handleGenericError(e: Exception) {
        // Handle other errors more gracefully (e.g., show a message to the user)
        e.printStackTrace()
        Config.errorToast(app.applicationContext, e)
    }
}
