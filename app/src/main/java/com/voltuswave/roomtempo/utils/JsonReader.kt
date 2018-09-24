package com.voltuswave.roomtempo.utils

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;

object JsonReader {
    private val TAG = JsonReader::class.java.simpleName
    @Throws(IOException::class)
    fun <T> readJsonStream(inputStream: InputStream, typeOfT:Type):T {
        val writer = StringWriter()
        val buffer = CharArray(4096)
        try
        {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            val pointer:Int = reader.read(buffer)
            while (pointer != -1)
            {
                writer.write(buffer, 0, pointer)
            }
        }
        finally
        {
            try
            {
                inputStream.close()
            }
            catch (exception:IOException) {
                Log.e(TAG, "Error closing the input stream.", exception)
            }
        }
        val jsonString = writer.toString()
        val gson = Gson()
        return gson.fromJson(jsonString, typeOfT)
    }
}