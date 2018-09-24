package com.voltuswave.roomtempo.models


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Profile : Serializable{

    @SerializedName("userFirstName")
    @Expose
    val userFirstName:String = ""
    @SerializedName("failedLoginAttempt")
    @Expose
    val failedLoginAttempt:Int = 0
    @SerializedName("token")
    @Expose
    val token:String = ""
    @SerializedName("email")
    @Expose
    val email:String = ""
    @SerializedName("userID")
    @Expose
    val userID:Int = 0
    @SerializedName("FailedLoginTime")
    @Expose
    val failedLoginTime:String = ""
    @SerializedName("clientName")
    @Expose
    val clientName:String = ""
    @SerializedName("clientCode")
    @Expose
    val clientCode:String = ""
    @SerializedName("userLastName")
    @Expose
    val userLastName:String = ""
    @SerializedName("clientID")
    @Expose
    val clientID:Int = 0
    @SerializedName("loginID")
    @Expose
    val loginID:String = ""
    @SerializedName("PropertyId")
    @Expose
    val propertyId:Int = 0

}