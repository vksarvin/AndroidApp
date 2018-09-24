package com.voltuswave.roomtempo.models

import com.google.gson.annotations.SerializedName

data class ReservationDetailStatusModel(
        @SerializedName("StatusId") val statusId: Int = 0,
        @SerializedName("StatusName") val statusName: String = ""
)