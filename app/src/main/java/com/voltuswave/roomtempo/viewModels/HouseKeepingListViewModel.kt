package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.voltuswave.roomtempo.models.HouseKeepingListDataModel
import com.voltuswave.roomtempo.network.StoredProcedureCalls



class HouseKeepingListViewModel(application: Application): AndroidViewModel(application){
    var storedProcedureCalls = StoredProcedureCalls()
    var housekeepinglist = MediatorLiveData<Array<HouseKeepingListDataModel?>?>()

    fun requestHouseKeepingListFromModel(eventName: String, dateObject: String, propertyId: Int, unitTypeId: Int, activity: FragmentActivity): MediatorLiveData<Array<HouseKeepingListDataModel?>?> {
        Log.e("HouseKeepingListFilter", "dateObject : $dateObject propertyId : $propertyId unitTypeId : $unitTypeId")
        val dataSource = storedProcedureCalls.getHouseKeepingList(eventName, dateObject, propertyId, unitTypeId,activity)
        housekeepinglist.addSource(dataSource)
        { it ->
            housekeepinglist.removeSource(dataSource)
            housekeepinglist.value = it
        }
        return housekeepinglist
    }
}