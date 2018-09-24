package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FolioItem {
    @SerializedName("FolioItemName")
    @Expose
    var folioItemName:String = ""
    @SerializedName("Charges")
    @Expose
    var charges:String = ""
    @SerializedName("ItemQty")
    @Expose
    var itemQty:Int? = 0
    @SerializedName("Adr")
    @Expose
    var adr:Int? = 0
}