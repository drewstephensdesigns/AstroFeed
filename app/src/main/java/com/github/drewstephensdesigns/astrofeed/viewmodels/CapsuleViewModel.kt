package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.drewstephensdesigns.astrofeed.data.local.model.Capsules
import com.github.drewstephensdesigns.astrofeed.data.local.model.Spacecraft
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CapsuleViewModel(private val app: Application) : AndroidViewModel(app){

    private var _capsulesList = ArrayList<Spacecraft>()
    private val _capsulesListObjects = MutableLiveData<List<Spacecraft>>()
    val capsulesListObjects: LiveData<List<Spacecraft>> get() = _capsulesListObjects

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    init {
        fetchCapsules()
    }

    private fun fetchCapsules(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.MOCK_LAUNCH_LIBRARY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO) {
                    service.getSpaceCraft(
                        50,
                        0,
                        false,
                        "Dragon"
                    )
                }


                //val sortedList = response.sortedWith{ rocketType1, rocketType2 ->
                //    rocketType1.type!!.compareTo(rocketType2.type!!)
               // }

                val sortedList = response.results.sortedByDescending { it.name }

                _capsulesList.clear()
                _capsulesList.addAll(sortedList)

                _capsulesListObjects.postValue(_capsulesList)

            } catch (e: Exception){
                Config.errorToast(app.applicationContext, e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    override fun onCleared() {
        // Clean up resources here
        super.onCleared()
    }
}