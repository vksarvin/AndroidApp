package com.voltuswave.roomtempo.fragments


import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.ZoomImage.TouchImageView
import com.voltuswave.roomtempo.models.ProfileModel
import com.voltuswave.roomtempo.services.sharepreferencesServices
import com.voltuswave.roomtempo.viewModels.ProfileDetailsViewModel
import org.json.JSONArray
import org.json.JSONObject


class ProfileFragment : Fragment() {
    private var profileDetailsDataViewModel:ProfileDetailsViewModel? = null
    private lateinit var edittextUserId:EditText
    private lateinit var edittextClientId:EditText
    private lateinit var edittextClientName:EditText
    private lateinit var edittextClientCode:EditText
    private lateinit var edittextLoginId:EditText
    private lateinit var edittextUserFirstName:EditText
    private lateinit var edittextUserLastName:EditText
    private lateinit var edittextEmail:EditText
    private lateinit var edittextUserPassword:EditText
    private lateinit var edittextStatusId:EditText
    private lateinit var edittextStatusName:EditText
    private lateinit var edittextIsPasswordExpired:EditText
    private lateinit var textviewProfileTitle:TextView
    private lateinit var textviewProfileEmail:TextView
    private lateinit var profileImage:ImageButton
    private lateinit var profileImageBackground:ImageView
    private lateinit var imageUpdateButton:ImageView
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    private var profileInformationLinearLayout: LinearLayout? = null
    private var CAMERA_REQUEST:Int = 100
    private var GALLERY_REQUEST:Int = 200
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profile, container, false)

      var phoneNumber = sharepreferencesServices.getValueFromPreferences(activity,"User_PhoneNumber");
        edittextUserId=view.findViewById(R.id.userid)
        edittextUserId.setText(phoneNumber)
        edittextClientId=view.findViewById(R.id.clientId)
        edittextClientName=view.findViewById(R.id.clientname)
        edittextClientCode=view.findViewById(R.id.clientcode)
        edittextLoginId=view.findViewById(R.id.loginid)
        edittextUserFirstName=view.findViewById(R.id.userfirstname)
        edittextUserLastName=view.findViewById(R.id.userlastname)
        edittextUserPassword=view.findViewById(R.id.userpassword)
        edittextEmail=view.findViewById(R.id.email)
        edittextStatusId=view.findViewById(R.id.statusid)

        edittextStatusName=view.findViewById(R.id.statusnameuser)
        edittextIsPasswordExpired=view.findViewById(R.id.passwordexpire)
        textviewProfileTitle=view.findViewById(R.id.user_profile_name)
        textviewProfileEmail=view.findViewById(R.id.user_profile_short_bio)
        profileImage=view.findViewById(R.id.user_profile_photo)
        profileImageBackground=view.findViewById(R.id.header_cover_image)
        imageUpdateButton=view.findViewById(R.id.add_photo_imageview)
        val inflater: LayoutInflater = LayoutInflater.from(activity!!)
        val dialogLayout = inflater.inflate(R.layout.dialogwithmoreinfo, container, false)
        val cancelButtonDialog=dialogLayout.findViewById<TextView>(R.id.canceltextview)
        val cameraButtonDialog=dialogLayout.findViewById<TextView>(R.id.camerabtntextview)
        val galleryButtonDialog=dialogLayout.findViewById<TextView>(R.id.gallerybtntextview)
        val customAlertDialog = PopupWindow(dialogLayout, resources.getDimensionPixelSize(R.dimen.relationshipdivider_moredialog_height), WindowManager.LayoutParams.WRAP_CONTENT, false)
        customAlertDialog.isTouchable = true
        customAlertDialog.isFocusable = true
        customAlertDialog.animationStyle = R.style.PopupAnimation
        customAlertDialog.isOutsideTouchable = true

        imageUpdateButton.setOnClickListener{
            customAlertDialog.showAsDropDown(it)
        }
        cancelButtonDialog.setOnClickListener {
            customAlertDialog.dismiss()
        }
        //---for camera
        cameraButtonDialog.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent,CAMERA_REQUEST);
            customAlertDialog.dismiss()
        }
        //------for gallery
        galleryButtonDialog.setOnClickListener {
            val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(galleryIntent,GALLERY_REQUEST);
            customAlertDialog.dismiss()
        }
        //---------
        profileImageBackground.setImageBitmap(decodeSampledBitmapFromResource(getResources(), R.drawable.imageloginpage, 100, 100));
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        profileInformationLinearLayout = view.findViewById(R.id.profileinformationlayout)
        //profileInformationLinearLayout!!.visibility=View.GONE
        profileDetailsDataViewModel=ViewModelProviders.of(this).get(ProfileDetailsViewModel::class.java)
        if (isConnected(context)){
         //   profileDetailsDataViewModel!!.requestprofileDetailsFromModel("GetUserProfile")
//            profileDetailsDataViewModel!!.getprofileDetailsList.observe(activity!!, Observer { t: JSONArray?->
//                Log.e("ProfileFragment","ProfileResponse : " + t.toString())
//                if (t!=null && t.length()>0){
//                    mShimmerViewContainer!!.stopShimmerAnimation()
//                    mShimmerViewContainer!!.visibility = View.GONE
//                    profileInformationLinearLayout!!.visibility=View.VISIBLE
//                    val profileJsonArray=JSONArray(t[0].toString())
//                    Log.e("ProfileFragment", "ProfileResponse : $profileJsonArray")
//                    val gson = Gson()
//                    for (index in 0 until profileJsonArray.length()) {
//                        val jsonObject=profileJsonArray[index] as JSONObject
//                        val profileArrayModelObject: ProfileModel = gson.fromJson(jsonObject.toString(), object : TypeToken<ProfileModel>() {}.type)
//                        textviewProfileTitle.text = profileArrayModelObject.userFirstName
//                        edittextClientId.setText("${profileArrayModelObject.clientId}")
//                        edittextClientName.setText(profileArrayModelObject.clientName)
//                        edittextClientCode.setText(profileArrayModelObject.clientCode)
//                        edittextLoginId.setText(profileArrayModelObject.loginId)
//                        edittextUserFirstName.setText(profileArrayModelObject.userFirstName)
//                        edittextEmail.setText(profileArrayModelObject.email)
//                        edittextUserPassword.setText(profileArrayModelObject.userPassword)
//                        edittextStatusId.setText("${profileArrayModelObject.statusId}")
//                        edittextStatusName.setText(profileArrayModelObject.statusName)
//                        textviewProfileEmail.text = profileArrayModelObject.email
//                    }
//                } else {
//                    Toast.makeText(activity!!,"Empty List", Toast.LENGTH_SHORT).show()
//                }
//            })


        }else{
            Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
        }
        return view
    }

    private fun isConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null
    }

    override fun onStart() {
        super.onStart()
        activity!!.title = "Profile Details"
    }
    //for large image
    private fun decodeSampledBitmapFromResource(res: Resources, resId:Int,
                                        reqWidth:Int, reqHeight:Int): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }
    private fun calculateInSampleSize(
            options:BitmapFactory.Options, reqWidth:Int, reqHeight:Int):Int {
        // Raw height and width of image
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth)
        {
            val halfHeight = height / 2
            val halfWidth = width / 2
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth))
            {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun onResume() {
        super.onResume()
        //mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
       // mShimmerViewContainer!!.stopShimmerAnimation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==CAMERA_REQUEST)
        {
            if(resultCode == RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
                profileImage.setImageBitmap(photo)
                profileImage.setOnClickListener { _ ->
                    val nagDialog = Dialog(context!!, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                    nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    nagDialog.setCancelable(false)
                    val img = TouchImageView(activity!!)
                    img.setImageBitmap(photo)
                    img.setMaxZoom(4f)
                    nagDialog.setContentView(img)
                    img.setOnClickListener { nagDialog.dismiss() }
                    nagDialog.show()
                }
            }
        }else if (requestCode ==GALLERY_REQUEST){
            if(resultCode == RESULT_OK) {
                val selectedImage = data!!.data
                    profileImage.setImageURI(selectedImage)
                    profileImage.setOnClickListener{ _ ->
                        val nagDialog = Dialog(context!!, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
                          nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                          nagDialog.setCancelable(false)
                           val img = TouchImageView(context!!)
                          img.setImageURI(selectedImage)
                          img.setMaxZoom(4f)
                          nagDialog.setContentView(img)
                         img.setOnClickListener { nagDialog.dismiss() }
                        nagDialog.show()
                }
            }
        }
    }
}
