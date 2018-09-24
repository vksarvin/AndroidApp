package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UnitDetail_ {
    @SerializedName("unitDetails")
    @Expose
    var unitDetails:UnitDetails? = null
    @SerializedName("assignedUsers")
    @Expose
    var assignedUsers:List<AssignedUser>? = null
}