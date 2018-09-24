package com.voltuswave.roomtempo.network

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetworkApi {
    @POST("api")
    fun getLoginInfo(@Body requestBody: RequestBody): Call<Any>
}