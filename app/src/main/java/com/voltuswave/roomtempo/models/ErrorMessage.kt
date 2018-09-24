package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ErrorMessage {
    @SerializedName("ErrorMessage")
    @Expose
    var errorMessage:String = ""
}