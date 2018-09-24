package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.support.v4.app.FragmentActivity
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import org.json.JSONArray


class GetUnitTypeListViewModel(application: Application): AndroidViewModel(application){
    var storedProcedureCalls = StoredProcedureCalls()
    var getUnitTypeList =  MediatorLiveData<JSONArray>()
    fun requestGetUnitTypeListFromModel(eventName: String, propertyId: Int, searchText: String?, activity: FragmentActivity): MediatorLiveData<JSONArray> {
        val dataSource = storedProcedureCalls.getUnitTypeData(eventName,propertyId, searchText,activity!!)
        getUnitTypeList.addSource(dataSource)
        { it ->
            getUnitTypeList.removeSource(dataSource)
            getUnitTypeList.value = it
        }
        return getUnitTypeList
    }

}