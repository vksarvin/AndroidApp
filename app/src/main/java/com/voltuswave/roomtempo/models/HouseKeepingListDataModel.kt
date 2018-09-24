package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

class HouseKeepingListDataModel {
    @SerializedName("usersList")
    @Expose
    var usersList: List<UsersList>? = null
    @SerializedName("unitDetails")
    @Expose
    var uniqueUnitDetail: Any? = null
}