package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class LedgerAccountItem {
    @SerializedName("ledgerAccountID")
    @Expose
    var ledgerAccountID:Int = 0
    @SerializedName("ledgerAccountName")
    @Expose
    var ledgerAccountName:String = ""
    @SerializedName("defaultAmount")
    @Expose
    var defaultAmount:Any? = null
}