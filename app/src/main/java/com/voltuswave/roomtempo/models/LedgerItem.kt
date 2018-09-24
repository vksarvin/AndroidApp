package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class LedgerItem {
    @SerializedName("ledgerAccountId")
    @Expose
    var ledgerAccountId:Int = 0
    @SerializedName("ItemId")
    @Expose
    var itemId:String = ""
    @SerializedName("ItemDescription")
    @Expose
    var itemDescription:String = ""
    @SerializedName("DateEffective")
    @Expose
    var dateEffective:String = ""
    @SerializedName("FolioAmount")
    @Expose
    var folioAmount:String = ""
    @SerializedName("TaxAmount")
    @Expose
    var taxAmount:String = ""
    @SerializedName("Total")
    @Expose
    var total:Float = 0.toFloat()
    @SerializedName("ItemStatusId")
    @Expose
    var itemStatusId:Int = 0
    @SerializedName("isedited")
    @Expose
    var isIsedited:Boolean = false
    @SerializedName("ispayment")
    @Expose
    var ispayment:Int = 0
    @SerializedName("IsVoided")
    @Expose
    var isVoided:Int = 0
}