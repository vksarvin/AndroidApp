package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import org.json.JSONArray

class FolioDetailViewModel(application: Application): AndroidViewModel(application){
    private var storedProcedureCalls = StoredProcedureCalls()
    var folioSummarydetail = MediatorLiveData<JSONArray>()

    fun requestFolioDetailFromModel(eventName: String, reservationId: Int): MediatorLiveData<JSONArray> {
        val dataSource = storedProcedureCalls.getGetFolioSummary(eventName,reservationId)
        folioSummarydetail.addSource(dataSource)
        { it ->
            folioSummarydetail.removeSource(dataSource)
            folioSummarydetail.value = it
        }
        return folioSummarydetail
    }
}