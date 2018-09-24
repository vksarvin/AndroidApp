package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UnitDetails {
    @SerializedName("taskId")
    @Expose
    var taskId:Int = 0
    @SerializedName("propertyId")
    @Expose
    var propertyId:Int = 0
    @SerializedName("propertyNickName")
    @Expose
    var propertyNickName:String = ""
    @SerializedName("unitId")
    @Expose
    var unitId:Int = 0
    @SerializedName("unitName")
    @Expose
    var unitName:String = ""
    @SerializedName("unitNickName")
    @Expose
    var unitNickName:String = ""
    @SerializedName("unitTypeId")
    @Expose
    var unitTypeId:Int = 0
    @SerializedName("unitTypeName")
    @Expose
    var unitTypeName:String = ""
    @SerializedName("noOfBedrooms")
    @Expose
    var noOfBedrooms:Int = 0
    @SerializedName("taskTypeId")
    @Expose
    var taskTypeId:Int = 0
    @SerializedName("tasktypeName")
    @Expose
    var tasktypeName:String = ""
    @SerializedName("houseKeeperStatus")
    @Expose
    var houseKeeperStatus:String = ""
    @SerializedName("housekeepingInChargeId")
    @Expose
    var housekeepingInChargeId:Any? = null
    @SerializedName("houseKeepingInChargeName")
    @Expose
    var houseKeepingInChargeName:Any? = null
    @SerializedName("housekeepingInChargeInitials")
    @Expose
    var housekeepingInChargeInitials:String = ""
    @SerializedName("housekeepingInChargeProfileImage")
    @Expose
    var housekeepingInChargeProfileImage:Any? = null
    @SerializedName("houseKeepingInchargeStatus")
    @Expose
    var houseKeepingInchargeStatus:Any? = null
    @SerializedName("priorReservationId")
    @Expose
    var priorReservationId:Int = 0
    @SerializedName("priorResGuestName")
    @Expose
    var priorResGuestName:String = ""
    @SerializedName("priorResCheckInDate")
    @Expose
    var priorResCheckInDate:String = ""
    @SerializedName("priorResCheckOutDate")
    @Expose
    var priorResCheckOutDate:String = ""
    @SerializedName("priorResCheckInTime")
    @Expose
    var priorResCheckInTime:Any? = null
    @SerializedName("priorResCheckOutTime")
    @Expose
    var priorResCheckOutTime:Any? = null
    @SerializedName("priorResGuests")
    @Expose
    var priorResGuests:Int = 0
    @SerializedName("priorResStatusId")
    @Expose
    var priorResStatusId:Int = 0
    @SerializedName("priorResStatusName")
    @Expose
    var priorResStatusName:String = ""
    @SerializedName("priorUnitStatus")
    @Expose
    var priorUnitStatus:String = ""
    @SerializedName("statusTypeId")
    @Expose
    var statusTypeId:Int = 0
    @SerializedName("statusTypeName")
    @Expose
    var statusTypeName:String = ""
    @SerializedName("nextReservationId")
    @Expose
    var nextReservationId:Int = 0
    @SerializedName("nextResGuestName")
    @Expose
    var nextResGuestName:String = ""
    @SerializedName("nextResCheckInDate")
    @Expose
    var nextResCheckInDate:String = ""
    @SerializedName("nextResCheckOutDate")
    @Expose
    var nextResCheckOutDate:String = ""
    @SerializedName("nextResCheckInTime")
    @Expose
    var nextResCheckInTime:Any? = null
    @SerializedName("nextResCheckOutTime")
    @Expose
    var nextResCheckOutTime:Any? = null
    @SerializedName("nextResGuests")
    @Expose
    var nextResGuests:Int = 0
    @SerializedName("nextResStatusId")
    @Expose
    var nextResStatusId:Int = 0
    @SerializedName("nextResStatusName")
    @Expose
    var nextResStatusName:String = ""
    @SerializedName("nextUnitStatus")
    @Expose
    var nextUnitStatus:String = ""
    @SerializedName("housekeepingNotesId")
    @Expose
    var housekeepingNotesId:Any? = null
    @SerializedName("taskstatusMetaData")
    @Expose
    var taskstatusMetaData:List<TaskstatusMetaDatum>? = null
}