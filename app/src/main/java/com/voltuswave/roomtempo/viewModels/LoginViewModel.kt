package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.voltuswave.roomtempo.models.Profile
import com.voltuswave.roomtempo.network.RetrofitRepository


class LoginViewModel(application: Application): AndroidViewModel(application) {
    private var retrofitRepository = RetrofitRepository()
    var loginLiveData = MediatorLiveData<Array<Profile>>()


    fun requestLoginFromModel(emailStr: String,clientIdstr: String,passwordStr: String): MediatorLiveData<Array<Profile>> {
        val dataSource = retrofitRepository.getStoreInfo(emailStr,clientIdstr,passwordStr)
        loginLiveData.addSource(dataSource)
        { it ->
            loginLiveData.removeSource(dataSource)
            loginLiveData.value = it
        }
        return loginLiveData
    }
}