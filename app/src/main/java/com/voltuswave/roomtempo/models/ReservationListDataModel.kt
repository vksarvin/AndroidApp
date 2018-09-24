package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ReservationListDataModel {
    @SerializedName("errorMessage")
    @Expose
    var errorMessage:List<ErrorMessage>? = null
    @SerializedName("reservationList")
    @Expose
    var reservationList:List<ReservationList>? = null
    @SerializedName("customViewObj")
    @Expose
    var customViewObj:Any? = null
    @SerializedName("customViewColumns")
    @Expose
    var customViewColumns:List<Any>? = null
}