package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

    class LoginWarning {
        @SerializedName("Warning")
        @Expose
        var warning:String = ""
        @SerializedName("token")
        @Expose
        var token:String = ""
    }
