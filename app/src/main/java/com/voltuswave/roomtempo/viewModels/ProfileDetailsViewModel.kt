package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.util.Log
import com.voltuswave.roomtempo.models.HouseKeepingListDataModel
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import org.json.JSONArray


class ProfileDetailsViewModel(application: Application): AndroidViewModel(application){
    var storedProcedureCalls = StoredProcedureCalls()
    var getprofileDetailsList =  MediatorLiveData<JSONArray>()

    fun requestprofileDetailsFromModel(eventName: String):  MediatorLiveData<JSONArray>{

        val dataSource = storedProcedureCalls.getProfileDetails(eventName)
        getprofileDetailsList.addSource(dataSource)
        { it ->
            getprofileDetailsList.removeSource(dataSource)
            getprofileDetailsList.value = it
        }
        return getprofileDetailsList
    }
}