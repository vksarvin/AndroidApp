package com.voltuswave.roomtempo.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ProfileModel {
    @SerializedName("UserId")
    @Expose
    var userId:Int = 0
    @SerializedName("ClientId")
    @Expose
    var clientId:Int = 0
    @SerializedName("ClientName")
    @Expose
    var clientName:String=""
    @SerializedName("ClientCode")
    @Expose
    var clientCode:String=""
    @SerializedName("LoginId")
    @Expose
    var loginId:String=""
    @SerializedName("UserFirstName")
    @Expose
    var userFirstName:String=""
    @SerializedName("UserLastName")
    @Expose
    var userLastName:String=""
    @SerializedName("Email")
    @Expose
    var email:String=""
    @SerializedName("UserPassword")
    @Expose
    var userPassword:String=""
    @SerializedName("StatusId")
    @Expose
    var statusId:Int = 0
    @SerializedName("StatusName")
    @Expose
    var statusName:String=""
    @SerializedName("IsPasswordExpired")
    @Expose
    var isPasswordExpired:Int = 0
    @SerializedName("isUserLocked")
    @Expose
    var isUserLocked:Int = 0
    @SerializedName("ProfileImage")
    @Expose
    var profileImage:String=""
}