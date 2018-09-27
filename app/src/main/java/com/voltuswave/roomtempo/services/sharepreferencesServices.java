package com.voltuswave.roomtempo.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

import com.voltuswave.roomtempo.authentication.PhoneActivityInside;

public class sharepreferencesServices {

    public static void storeValueInPreferences(PhoneActivityInside phoneActivityInside, String user_phoneNumber, String phone){

        SharedPreferences sharedPreferences = phoneActivityInside.getSharedPreferences("sharedpreferencesobject", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putString(user_phoneNumber, phone);
              editor.apply();

    }

    public static String getValueFromPreferences(FragmentActivity activity, String s){
        SharedPreferences sharedPreferences = activity.getSharedPreferences("sharedpreferencesobject", Context.MODE_PRIVATE);
        return sharedPreferences.getString(s, null);


    }
}
