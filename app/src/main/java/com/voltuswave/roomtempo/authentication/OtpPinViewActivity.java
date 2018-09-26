package com.voltuswave.roomtempo.authentication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.voltuswave.roomtempo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.READ_CONTACTS;


public class OtpPinViewActivity extends AppCompatActivity  {
TextView textViewTime;
EditText editTextOtpField;
Button btnOtpVerify;
String otpnumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppinview);
        textViewTime=findViewById(R.id.time);
        editTextOtpField=findViewById(R.id.editTextotp);
        btnOtpVerify=findViewById(R.id.btnVerify);

      btnOtpVerify.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
              otpnumber=editTextOtpField.getText().toString();
              if (otpnumber.isEmpty()){

                  Toast.makeText(OtpPinViewActivity.this,"Please Type Otp!!",Toast.LENGTH_SHORT).show();
              }else {
                  Intent returnIntent = new Intent();
                  returnIntent.putExtra("otp",otpnumber);
                  setResult(Activity.RESULT_OK,returnIntent);
                  finish();
              }


          }
      });
    }
}

