package com.voltuswave.roomtempo.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.support.v4.app.FragmentActivity
import com.voltuswave.roomtempo.models.ReservationListDataModel
import com.voltuswave.roomtempo.network.StoredProcedureCalls

class ReservationListViewModel(application: Application): AndroidViewModel(application) {
    var storedProcedureCalls = StoredProcedureCalls()
    var reservationlist = MediatorLiveData<Array<ReservationListDataModel?>?>()


    fun requestReservationsListFromModel(eventName: String, activity: FragmentActivity): MediatorLiveData<Array<ReservationListDataModel?>?> {
        val dataSource = storedProcedureCalls.getReservationsList(eventName,activity!!)
        reservationlist.addSource(dataSource)
        { it ->
            reservationlist.removeSource(dataSource)
            reservationlist.value = it
        }
        return reservationlist
    }
}