package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import org.json.JSONArray

class HouseKeepingDetailViewModel(application: Application): AndroidViewModel(application){
    private var storedProcedureCalls = StoredProcedureCalls()
    var housekeepingdetail = MediatorLiveData<JSONArray>()

    fun requestHouseKeepingDetailFromModel(eventName: String, reservationId: Int): MediatorLiveData<JSONArray> {
        val dataSource = storedProcedureCalls.getReservationDetailsSummary(eventName,reservationId)
        housekeepingdetail.addSource(dataSource)
        { it ->
            housekeepingdetail.removeSource(dataSource)
            housekeepingdetail.value = it
        }
        return housekeepingdetail
    }
}