package com.voltuswave.roomtempo.network

import com.voltuswave.roomtempo.utils.StaticConstants
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException


class SingletonSocket {

    val instance: Socket?
        get() {
            if (uniqInstance == null) {
                synchronized(this@SingletonSocket) {

                    try {
                        uniqInstance = IO.socket(StaticConstants.url)
                    } catch (e: URISyntaxException) {
                        e.printStackTrace()
                    }

                }
            }
            return uniqInstance
        }

    companion object {
        var uniqInstance: Socket? = null
    }
    // other useful methods here

}