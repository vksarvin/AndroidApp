package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReservationList : Serializable {
    @SerializedName("ClientId")
    @Expose
    var clientId:Int = 0
    @SerializedName("ClientName")
    @Expose
    var clientName:String = ""
    @SerializedName("GuestProfileId")
    @Expose
    var guestProfileId:Int = 0
    @SerializedName("GuestFirstName")
    @Expose
    var guestFirstName:String = ""
    @SerializedName("GuestLastName")
    @Expose
    var guestLastName:String = ""
    @SerializedName("NumberOfAdults")
    @Expose
    var numberOfAdults:Int = 0
    @SerializedName("NumberOfChildren")
    @Expose
    var numberOfChildren:Int = 0
    @SerializedName("UnitId")
    @Expose
    var unitId:Int = 0
    @SerializedName("ReservationId")
    @Expose
    var reservationId:Int = 0
    @SerializedName("UnitTypeId")
    @Expose
    var unitTypeId:Int = 0
    @SerializedName("UnitName")
    @Expose
    var unitName:String = ""
    @SerializedName("UnitNickName")
    @Expose
    var unitNickName:String = ""
    @SerializedName("UnitTypeName")
    @Expose
    var unitTypeName:String = ""
    @SerializedName("StartDate")
    @Expose
    var startDate:String = ""
    @SerializedName("EndDate")
    @Expose
    var endDate:String = ""
    @SerializedName("TotalRate")
    @Expose
    var totalRate:Float = 0.0F
    @SerializedName("BalanceRate")
    @Expose
    var balanceRate:Float = 0.0F
    @SerializedName("Nights")
    @Expose
    var nights:Int = 0
    @SerializedName("PropertyId")
    @Expose
    var propertyId:Int = 0
    @SerializedName("StatusId")
    @Expose
    var statusId:Int = 0
    @SerializedName("StatusName")
    @Expose
    var statusName:String = ""
    @SerializedName("DateBooked")
    @Expose
    var dateBooked:String = ""
    @SerializedName("SourceId")
    @Expose
    var sourceId:Int = 0
    @SerializedName("SourceName")
    @Expose
    var sourceName:String = ""
    @SerializedName("BookedBy")
    @Expose
    var bookedBy:String = ""
    @SerializedName("PartyAddressId")
    @Expose
    var partyAddressId:Int = 0
    @SerializedName("Persons")
    @Expose
    var persons:Int = 0
    @SerializedName("PropertyName")
    @Expose
    var propertyName:String = ""
    @SerializedName("propertyNickName")
    @Expose
    var propertyNickName:String = ""
    @SerializedName("Email")
    @Expose
    var email:String = ""
    @SerializedName("PhoneNumber")
    @Expose
    var phoneNumber:String = ""
    @SerializedName("SubSourceId")
    @Expose
    var subSourceId:Any? = null
    @SerializedName("SubSourceName")
    @Expose
    var subSourceName:Any? = null
    @SerializedName("ArrivalDate")
    @Expose
    var arrivalDate:String = ""
    @SerializedName("CrsId")
    @Expose
    var crsId:Int = 0
    @SerializedName("CrsName")
    @Expose
    var crsName:String = ""
    @SerializedName("SourceConfirmationNumber")
    @Expose
    var sourceConfirmationNumber:Any? = null
    @SerializedName("CrsConfirmationNumber")
    @Expose
    var crsConfirmationNumber:Any? = null
    @SerializedName("CityName")
    @Expose
    var cityName:String = ""
    @SerializedName("StateName")
    @Expose
    var stateName:String = ""
    @SerializedName("CountryName")
    @Expose
    var countryName:String = ""
    @SerializedName("ConfirmationNumber")
    @Expose
    var confirmationNumber:String = ""
    @SerializedName("GuestFullName")
    @Expose
    var guestFullName:String = ""
    @SerializedName("ActualStartDate")
    @Expose
    var actualStartDate:String = ""
    @SerializedName("ActualEndDate")
    @Expose
    var actualEndDate:String = ""
    @SerializedName("ListColourcode")
    @Expose
    var listColourcode:Any? = null
    @SerializedName("IsActionEnable")
    @Expose
    var isIsActionEnable:Boolean = false
    @SerializedName("nextStatusId")
    @Expose
    var nextStatusId:Int = 0
    @SerializedName("Timezone")
    @Expose
    var timezone:String = ""
    @SerializedName("originalStartDate")
    @Expose
    var originalStartDate:String = ""
    @SerializedName("originalEndDate")
    @Expose
    var originalEndDate:String = ""
    @SerializedName("isCheckIn")
    @Expose
    var isIsCheckIn:Boolean = false
    @SerializedName("isCheckOut")
    @Expose
    var isIsCheckOut:Boolean = false
}
