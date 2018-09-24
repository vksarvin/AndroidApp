package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TaskstatusMetaDatum {
    @SerializedName("statusTypeId")
    @Expose
    var statusTypeId:Int = 0
    @SerializedName("statusName")
    @Expose
    var statusName:String = ""
}