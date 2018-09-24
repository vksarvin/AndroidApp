package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.support.v4.app.FragmentActivity
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import org.json.JSONArray


class GetPropertyListViewModel(application: Application): AndroidViewModel(application){
    var storedProcedureCalls = StoredProcedureCalls()
    var getpropertylist =  MediatorLiveData<JSONArray>()

    fun requestgetpropertyListFromModel(eventName: String, activity: FragmentActivity): MediatorLiveData<JSONArray> {
        val dataSource = storedProcedureCalls.getPropertyMetaData(eventName,0,activity)
        getpropertylist.addSource(dataSource)
        { it ->
            getpropertylist.removeSource(dataSource)
            getpropertylist.value = it
        }
        return getpropertylist
    }

}