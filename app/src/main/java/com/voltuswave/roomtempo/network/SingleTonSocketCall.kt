package com.voltuswave.roomtempo.network

import android.util.Log
import com.voltuswave.roomtempo.Interface.SessionExpired
import com.voltuswave.roomtempo.MainActivity
import com.voltuswave.roomtempo.shared.interfaces.OnSocketSuccessCallBack
import io.socket.client.Ack
import io.socket.client.Socket
import org.json.JSONObject

class SingleTonSocketCall {
    fun getDataFromServer(onSocketSuccessCallBack: OnSocketSuccessCallBack, keyTag: String, payload: JSONObject, socket: Socket) {
        Log.e("singletonSockCall", "=====$keyTag=====")
        Log.e("singletonSockCall", keyTag)
        Log.e("singletonSockCall", payload.toString())
        Log.e("singletonSockCall", "=====END of $keyTag======")
        socket.emit(keyTag, payload, Ack { args ->
            if (args != null) {
                if (args[0] != null) {
                    Log.e("singletonSockCallArg[0]", args[0].toString())
                    val jsonObject = JSONObject(args[0].toString())
                    val boolean = jsonObject.has("message")
                    if (boolean) {
                        val message = jsonObject.getString("message")
                        if (message == "Session Expired"){
                            onSocketSuccessCallBack.onSuccessDataReady(args)
                            Log.e("singletonSockCall", message)
                        }
                    }else {
                        onSocketSuccessCallBack.onSuccessDataReady(null)
                    }
                } else if (args[1] != null) {
                    Log.e("singletonSockCall", "=====$keyTag=====")
                    Log.e("singletonSockCallArg[1]", args[1].toString())
                    Log.e("singletonSockCall", "=====$keyTag=====")
                    onSocketSuccessCallBack.onSuccessDataReady(args)
                }
            }
        })
    }
}