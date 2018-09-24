package com.voltuswave.roomtempo.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.voltuswave.roomtempo.models.Profile
import com.voltuswave.roomtempo.utils.StaticConstants
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors


class RetrofitRepository {
    private var jsonParams = JSONObject()
    private var requestParams = JSONObject()
    private var systemParams = JSONObject()
    var retrofit: Retrofit? = null

    var data = MutableLiveData<Array<Profile>>()

    private fun getRetrofitClient(): Retrofit {
        if (retrofit == null) {
            Log.e("Retrofit URL","Retrofit URL : "+ StaticConstants.url)
            val gson= GsonBuilder()
                    .setLenient()
                    .create()
            retrofit = Retrofit.Builder()

                    .baseUrl(StaticConstants.url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    //execute call back in background thread
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build()

        }
        return this.retrofit!!
    }

    fun getIntData(): LiveData<Array<Profile>> {
        return data
    }

    fun getStoreInfo(emailStr: String, clientIdstr: String, passwordStr: String) : LiveData<Array<Profile>>{
        Log.d("", "PROCESSING IN THREAD BEFORE RETROFIT CALL " + Thread.currentThread().name)
        requestParams.put("APIReg", "10003")
        requestParams.put("UserName", emailStr)
        requestParams.put("ClientCode", clientIdstr)
        requestParams.put("Password", passwordStr)
        requestParams.put("FunctionId", "fncLogin")

        systemParams.put("Source", "Android")
        systemParams.put("SourceId", StaticConstants.sourceId)

        jsonParams.put("requestParams", requestParams)
        jsonParams.put("systemParams", systemParams)
        val body = RequestBody.create(okhttp3.MediaType.parse("application/json; " +
                "charset=utf-8"), jsonParams.toString())
        val call = getRetrofitClient().create(NetworkApi::class.java).getLoginInfo(body)

        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>?, response: Response<Any>) {
                if (response.body() != null) {
                    val gson = Gson()
                    val jsonString: String? = gson.toJson(response.body())
                    Log.e("LoginResponse", jsonString)
                    val json = JSONTokener(jsonString).nextValue()

                    if (json is JSONArray){
                        Log.e("LoginResponse","INSIDE JSONARRAY")
                        if(json.getJSONObject(0).has("Warning") || json.getJSONObject(0).has("LoginMessage") ){
                            Log.e("LoginResponse","INSIDE WARNING")
                            data.postValue(null)
                        }else{
                            Log.e("LoginResponse","INSIDE PROFILE")
                            val profileArrayModelObject: Array<Profile> = gson.fromJson(jsonString, object : TypeToken<Array<Profile>>() {}.type)
                            data.postValue(profileArrayModelObject)
                            }
                        }
                    }else{
                    data.postValue(null)
                }
                }


            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.e("LoginResponse", "Login Response Failed")
            }
        })
        return data
    }
}