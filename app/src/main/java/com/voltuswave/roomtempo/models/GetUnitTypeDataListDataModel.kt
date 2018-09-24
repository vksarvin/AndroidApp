package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class GetUnitTypeDataListDataModel {

    @SerializedName("UnitTypeId")
    @Expose
    val unitTypeId:Int = 0
    @SerializedName("UnitTypeName")
    @Expose
    val unitTypeName:String = ""
}