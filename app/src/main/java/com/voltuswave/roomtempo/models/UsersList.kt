package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UsersList {
    @SerializedName("userId")
    @Expose
    var userId:Int = 0
    @SerializedName("userFullName")
    @Expose
    var userFullName:String = ""
    @SerializedName("profileImage")
    @Expose
    var profileImage:String = ""
    @SerializedName("initials")
    @Expose
    var initials:String = ""
    @SerializedName("isChecked")
    @Expose
    var isChecked:Boolean = false
}