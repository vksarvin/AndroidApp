package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
class TotalFolioItem {
    @SerializedName("TotalFolio")
    @Expose
    var totalFolio:String = ""
    @SerializedName("TotalTax")
    @Expose
    var totalTax:String = ""
    @SerializedName("TotalValue")
    @Expose
    var totalValue:String = ""
    @SerializedName("Payments")
    @Expose
    var payments:String = ""
    @SerializedName("Balance")
    @Expose
    var balance:String = ""
}