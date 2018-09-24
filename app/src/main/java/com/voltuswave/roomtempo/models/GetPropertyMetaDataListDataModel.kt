package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class GetPropertyMetaDataListDataModel {

    @SerializedName("ClientId")
    @Expose
    val clientId:Int = 0

    @SerializedName("PropertyId")
    @Expose
    val propertyId:Int = 0
    @SerializedName("PropertyName")
    @Expose
    val propertyName:String = ""
}