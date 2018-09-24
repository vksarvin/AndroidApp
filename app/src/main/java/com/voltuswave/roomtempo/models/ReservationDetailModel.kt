package com.voltuswave.roomtempo.models

import com.google.gson.annotations.SerializedName

data class ReservationDetailModel(
        @SerializedName("ReservationId") val reservationId: Int = 0,
        @SerializedName("GuestProfileId") val guestProfileId: Int = 0,
        @SerializedName("PartyAddressId") val partyAddressId: Int = 0,
        @SerializedName("GuestFirstName") val guestFirstName: String = "",
        @SerializedName("GuestLastName") val guestLastName: String = "",
        @SerializedName("GuestFullName") val guestFullName: String = "",
        @SerializedName("PropertyId") val propertyId: Int = 0,
        @SerializedName("PropertyName") val propertyName: String = "",
        @SerializedName("PropertyNickName") val propertyNickName: String = "",
        @SerializedName("UnitId") val unitId: Int = 0,
        @SerializedName("UnitName") val unitName: String = "",
        @SerializedName("UnitNickName") val unitNickName: String = "",
        @SerializedName("arrivalDate") val arrivalDate: String = "",
        @SerializedName("departureDate") val departureDate: String = "",
        @SerializedName("Nights") val nights: Int = 0,
        @SerializedName("checkInTime") val checkInTime: Any? = Any(),
        @SerializedName("checkOutTime") val checkOutTime: Any? = Any(),
        @SerializedName("StatusId") val statusId: Int = 0,
        @SerializedName("StatusName") val statusName: String = "",
        @SerializedName("NumberOfAdults") val numberOfAdults: Int = 0,
        @SerializedName("NumberOfChildren") val numberOfChildren: Int = 0,
        @SerializedName("Persons") val persons: Int = 0,
        @SerializedName("CityId") val cityId: Int = 0,
        @SerializedName("") val x: List<String> = listOf(),
        @SerializedName("StateId") val stateId: Int = 0,
        @SerializedName("CountryId") val countryId: Int = 0,
        @SerializedName("reservationMode") val reservationMode: Int = 0,
        @SerializedName("unitLogo") val unitLogo: String = "",
        @SerializedName("UnitTypeId") val unitTypeId: Int = 0,
        @SerializedName("UnitTypeName") val unitTypeName: String = ""
)