package com.github.drewstephensdesigns.astrofeed.viewmodels

import android.app.Application
import android.provider.CalendarContract.CalendarCache
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.drewstephensdesigns.astrofeed.data.local.model.Rocket
import com.github.drewstephensdesigns.astrofeed.data.local.model.SpaceX
import com.github.drewstephensdesigns.astrofeed.data.remote.SpaceService
import com.github.drewstephensdesigns.astrofeed.utils.Config
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class CompanyViewModel(private val app: Application) :  AndroidViewModel(app){

    private val _companyData = MutableLiveData<SpaceX>()
    val companyData: LiveData<SpaceX> = _companyData

    init {
        fetchCompany()
    }

    fun getCompanyInfo(): LiveData<SpaceX> = companyData

    private fun fetchCompany(){
        viewModelScope.launch {
            try {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Config.MOCK_LAUNCH_LIBRARY_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(SpaceService::class.java)
                val response = withContext(Dispatchers.IO){
                    service.getCompanyInfo()
                }

                response.body()?.let {
                    _companyData.postValue(it)
               }
            }
            catch (e: Exception){
                Config.errorToast(app.applicationContext, e)
            }
        }
    }
}