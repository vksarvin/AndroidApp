package com.voltuswave.roomtempo.authentication


import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.crashlytics.android.Crashlytics
import com.voltuswave.roomtempo.MainActivity
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.fragments.SpinnerAdapter
import com.voltuswave.roomtempo.models.Profile
import com.voltuswave.roomtempo.network.RetrofitRepository
import com.voltuswave.roomtempo.network.SingletonSocket
import com.voltuswave.roomtempo.services.SharedPreferenceService
import com.voltuswave.roomtempo.utils.StaticConstants
import com.voltuswave.roomtempo.utils.StringConstants
import com.voltuswave.roomtempo.viewModels.LoginViewModel
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private var loginViewModel: LoginViewModel? = null
    private var retrofitRepository = RetrofitRepository()
    private val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Fabric.with(this, Crashlytics())

        handler.postDelayed({
            if (SharedPreferenceService.getValue(applicationContext, StringConstants.key_token) != null) {
                // Token present -> Homepage (Main Activity)
                    StaticConstants.token = SharedPreferenceService.getValue(applicationContext, StringConstants.key_token)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
            }
        }, StaticConstants.SCREEN_TIMEOUT)

        // Set up the login form.
        btnphone.setOnClickListener {

            val intent = Intent(this@LoginActivity, PhoneActivityInside::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in, R.anim.nothing);
        }
        val savedUrl = SharedPreferenceService.getValue(this,StringConstants.key_savedUrl)
        if (savedUrl==null){
          //  showDialogForUrlAndLoginType()
        }

        //
        email_sign_in_button.setOnClickListener { attemptLogin() }

        rootlayout.setOnClickListener {
            hideKeyboard(it)
        }
        login_form.setOnClickListener {
            hideKeyboard(it)
        }

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {

        val intent = Intent(this@LoginActivity, LoginActivityInside::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in, R.anim.nothing);

}

    private fun isUserNameValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return email.isNotEmpty()
    }

    private fun isClientIdValid(clientId: String): Boolean {
        //TODO: Replace this with your own logic
        return clientId.isNotEmpty()
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.isNotEmpty()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    
    private fun showDialogForUrlAndLoginType() {
        val inflater: LayoutInflater = LayoutInflater.from(this)
        val nullParent: ViewGroup? = null
        val dialogLayout = inflater.inflate(R.layout.customalertdialog_login_layout_setting, nullParent, false)
        val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
        val urlSpinner = dialogLayout.findViewById<Spinner>(R.id.selectUrl)
        val editTextUrl=dialogLayout.findViewById<EditText>(R.id.edittextUrl)
        val savedUrl = SharedPreferenceService.getValue(this,StringConstants.key_savedUrl)
        builder.setCancelable(false)
        if (savedUrl != null)
        {
            editTextUrl.setText(savedUrl)
            StaticConstants.url=""
            StaticConstants.sourceId=""
            if (SingletonSocket.uniqInstance!=null){
                if(SingletonSocket.uniqInstance!!.connected()){
                    SingletonSocket.uniqInstance!!.disconnect()
                    SingletonSocket.uniqInstance=null
                    retrofitRepository.retrofit=null
                }
            }
            StaticConstants.url = editTextUrl.text.trim().toString()
            when {
                editTextUrl.text.trim().toString().contains("dev",true) -> StaticConstants.sourceId="dev.roomtempo.com"
                editTextUrl.text.trim().toString().contains("qa",true) -> StaticConstants.sourceId="qa.roomtempo.com"
                else -> {
    
                }
            }
        }else{
            editTextUrl.hint = "Enter Url"
        }
        val selectUrlArrayList = mutableListOf<String>()
        selectUrlArrayList.add("http://dev.roomtempo.com/")
        selectUrlArrayList.add("http://qa.roomtempo.com/")
        val propertyDataModelArrayAdapter = SpinnerAdapter(this, R.layout.spinner_item, selectUrlArrayList, "Url" )
        urlSpinner.adapter = propertyDataModelArrayAdapter
        var selectedUrl: String?
        urlSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                if (view!=null){
                    (view as TextView).setPadding(16, 0, 0, 0)//remove extra padding
                    view.setTextColor(Color.parseColor("#000000"))
                    view.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this@LoginActivity, R.drawable.ic_arrow_drop_down_black_24dp), null)
                    view.compoundDrawablePadding = 10// to set drawablePadding
                      if (position > 0) {
                          selectedUrl= selectUrlArrayList[position - 1]
                          editTextUrl.setText(selectedUrl)
                          view.text = "Url"
                      }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        builder.setView(dialogLayout)
        val customAlertDialog = builder.create()
        customAlertDialog.show()
        val btnSave: Button = dialogLayout.findViewById(R.id.btnsearchbuttonpopup)
        val btnCancel: Button = dialogLayout.findViewById(R.id.btnresetbuttonpopup)
        btnCancel.setOnClickListener{
            customAlertDialog.dismiss()
        }
        btnSave.setOnClickListener {
                if (editTextUrl.text.isNotEmpty()){
                    StaticConstants.url=""
                    StaticConstants.sourceId=""
                    if (SingletonSocket.uniqInstance!=null){
                        if(SingletonSocket.uniqInstance!!.connected()){
                            SingletonSocket.uniqInstance!!.disconnect()
                            SingletonSocket.uniqInstance=null
                            retrofitRepository.retrofit = null
                        }
                    }
                    StaticConstants.url=editTextUrl.text.trim().toString()
                   val urlfromedittext= editTextUrl.text.trim().toString()
                    SharedPreferenceService.storeUserDetails(applicationContext, StringConstants.key_savedUrl, urlfromedittext)

                    when {
                        editTextUrl.text.trim().toString().contains("dev",true) -> StaticConstants.sourceId="dev.roomtempo.com"
                        editTextUrl.text.trim().toString().contains("qa",true) -> StaticConstants.sourceId="qa.roomtempo.com"
                        else -> {

                        }
                    }
                    Toast.makeText(this,"Url has been saved",Toast.LENGTH_SHORT).show()
                    customAlertDialog.dismiss()
                }else{
                    val shake = AnimationUtils.loadAnimation(this, R.anim.shake1)
                    editTextUrl.startAnimation(shake)
                    customAlertDialog.show()
                }
        }
        Log.e("FinalUrl","url : "+ StaticConstants.url +" sourceId : "+StaticConstants.sourceId)
    }
    private fun hideKeyboard(it: View) {
        val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }

}