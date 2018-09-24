package com.voltuswave.roomtempo.network

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.authentication.LoginActivity
import com.voltuswave.roomtempo.fragments.HouseKeepingFragment
import com.voltuswave.roomtempo.fragments.ReservationDetailFragment
import com.voltuswave.roomtempo.fragments.ReservationListFragment
import com.voltuswave.roomtempo.models.GetPropertyMetaDataListDataModel
import com.voltuswave.roomtempo.models.GetUnitTypeDataListDataModel
import com.voltuswave.roomtempo.models.HouseKeepingListDataModel
import com.voltuswave.roomtempo.models.ReservationListDataModel
import com.voltuswave.roomtempo.services.SharedPreferenceService
import com.voltuswave.roomtempo.shared.interfaces.OnSocketSuccessCallBack
import com.voltuswave.roomtempo.utils.StaticConstants
import io.socket.client.Socket
import org.json.JSONArray
import org.json.JSONObject


class StoredProcedureCalls {

    private var singletonSocketCall: SingleTonSocketCall = SingleTonSocketCall()
    private var socket: Socket? = null
    private val reservationListPayload = JSONObject()
    private val systemParams = JSONObject()

    //Get property Meta data
    private val getPropertyMetaDataListPayload = JSONObject()
    val getPropertyMetaDataListMutableData = MutableLiveData<JSONArray>()
    var arraygetPropertyMetaDataList: Array<GetPropertyMetaDataListDataModel?>? = null
    //---------
    //Get Unit Type data
    private val getUnitTypeDataListPayload = JSONObject()

    var arraygetgetUnitTypeDataList: Array<GetUnitTypeDataListDataModel?>? = null
    var arraygetgetUnitTypeTotalDataList: ArrayList<Array<GetUnitTypeDataListDataModel?>>? = null
    //---------
    private val updateTaskStatusPayload = JSONObject()
    private val updateReservationStatusPayload = JSONObject()
    private val getFolioSummaryPayload = JSONObject()
    private val assignHousekeepersPayload = JSONObject()
    private val getReservationDetailsSummaryPayload = JSONObject()
    val reservationListMutableData = MutableLiveData<Array<ReservationListDataModel?>?>()
    var arrayReservationList: Array<ReservationListDataModel?>? = null
    val reservationDetailMutableData = MutableLiveData<JSONArray>()
    var arrayReservationDetail: JSONArray? = null
    val reservationStatusMutableData = MutableLiveData<JSONArray>()
    var arrayReservationDetailStatus: JSONArray? = null
    val folioSummaryMutableData = MutableLiveData<JSONArray>()
    var arrayFolioSummary: JSONArray? = null

    val gson = GsonBuilder().serializeNulls().create()!!

    fun getReservationsList(eventName: String, activity: FragmentActivity): LiveData<Array<ReservationListDataModel?>?> {

        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        reservationListPayload.put("PropertyId", -99)
        reservationListPayload.put("UnitTypeId", -99)
        reservationListPayload.put("ViewListId", -1)
        reservationListPayload.put("SortBy", null)
        reservationListPayload.put("SortOrder", null)
        reservationListPayload.put("resSourceId", -99)
        reservationListPayload.put("TagsList", JSONArray())
        reservationListPayload.put("SearchText", null)
        reservationListPayload.put("Properties", JSONArray())
        reservationListPayload.put("UnitTypes", JSONArray())
        reservationListPayload.put("Sources", JSONArray())
        reservationListPayload.put("Units", JSONArray())
        reservationListPayload.put("StatusList", JSONArray())
        reservationListPayload.put("Columns", JSONArray())
        reservationListPayload.put("PageNumber", 1)
        reservationListPayload.put("ItemsPerPage", 20)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        reservationListPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
             //   Log.e("getReservationList", success!![0].toString())
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!=null) {
                        if (success[0] == null) {
                            if (success[1]!!.toString() != "{}") {

                                arrayReservationList = gson.fromJson<Array<ReservationListDataModel?>>("[" + success[1].toString() + "]", Array<ReservationListDataModel?>::class.java)

                                reservationListMutableData.postValue(arrayReservationList)

                            } else {
                                reservationListMutableData.postValue(null)
                            }
                        } else {
                            sessionExpiredDialog(activity!!)
                            Log.e("suuccckeep","=session expired")
                        }
                    }
                }
            }
        }, eventName, reservationListPayload, socket!!)
        return reservationListMutableData
    }

    fun getHouseKeepingList(eventName: String, dateObjValue: String, propertyId: Int, unitTypeId: Int, activity: FragmentActivity): LiveData<Array<HouseKeepingListDataModel?>?> {
        val houseKeepingListMutableData = MutableLiveData<Array<HouseKeepingListDataModel?>?>()
        var arrayHouseKeepingList: Array<HouseKeepingListDataModel?>? = null
         val houseKeepingListPayload = JSONObject()
         val systemParams = JSONObject()
        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }
        houseKeepingListPayload.put("ModuleAction", "getHouseKeepinglist")
        houseKeepingListPayload.put("dateObjValue", dateObjValue)
       if (propertyId == 0){
           houseKeepingListPayload.put("propertyId", null)
       }else{
           houseKeepingListPayload.put("propertyId", propertyId)
       }
       if (unitTypeId == 0){
           houseKeepingListPayload.put("unitTypeId", null)
       }else {
           houseKeepingListPayload.put("unitTypeId", unitTypeId)
       }
        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        houseKeepingListPayload.put("systemParams", systemParams)
        Log.e("HouseKeepingResponse", "houseKeepingListPayload : $houseKeepingListPayload")
        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                val h = Handler(Looper.getMainLooper())
                h.post {
                    Log.e("suuccckeep","="+success!![0])
                    if (success!=null && success.isNotEmpty()) {
                        if (success[0] == null) {
                            Log.e("suuccckeep","=null check")
                            if (success[1]!!.toString() != "{}") {
                                arrayHouseKeepingList = gson.fromJson<Array<HouseKeepingListDataModel?>>("[" + success[1].toString() + "]", Array<HouseKeepingListDataModel?>::class.java)
                                Log.e("HouseKeepingResponse","houseKeepingList : " + success[1].toString())
                                houseKeepingListMutableData.postValue(arrayHouseKeepingList)
                            } else {
                                houseKeepingListMutableData.postValue(null)
                            }
                        }else{
                            sessionExpiredDialog(activity!!)
                            Log.e("suuccckeep","=session expired")


                        }
                    }
                }
            }
        }, eventName, houseKeepingListPayload, socket!!)
        return houseKeepingListMutableData
    }
    //----Get Property Meta Data
    fun getPropertyMetaData(eventName: String, propertyId: Int, activity: FragmentActivity): LiveData<JSONArray> {

        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        getPropertyMetaDataListPayload.put("ModuleAction", "GetPropertyMetaData")
        getPropertyMetaDataListPayload.put("PropertyId", JSONObject.NULL)
        systemParams.put("Source", "Android")
        systemParams.put("SourceId", StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        getPropertyMetaDataListPayload.put("systemParams", systemParams)
        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                Log.e("getPropertyMetaData", success.toString())
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!=null && success.size>=0) {

                        if (success!![0] == null) {
                            if (success[1]!!.toString() != "{}") {
                                Log.e("responceforpro","=="+success[1].toString())
                                val jsonObject=JSONObject(success[1].toString())
                                val recordsets=jsonObject.getJSONArray("recordsets")
                                val jsonObjectAtOne=JSONArray(recordsets[1].toString())
                                getPropertyMetaDataListMutableData.postValue(jsonObjectAtOne)
                            } else {
                                getPropertyMetaDataListMutableData.postValue(null)
                            }
                        }else{
                           // sessionExpiredDialog(activity!!)
                            Log.e("suuccckeep","=session expired")


                        }
                    }
                }
            }
        }, eventName, getPropertyMetaDataListPayload, socket!!)
        return getPropertyMetaDataListMutableData
    }

    //----Get Unit Type  Data
    fun getUnitTypeData(eventName: String, propertyId: Int, searchText: String?, activity: FragmentActivity): LiveData<JSONArray>{
        var unitTypeList = JSONArray()
        val getUnitTypeDataDataListMutableData = MutableLiveData<JSONArray>()
        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }
        getUnitTypeDataListPayload.put("searchText", searchText)
        if (propertyId == 0){
            getUnitTypeDataListPayload.put("PropertyId", null)
        }else{
            getUnitTypeDataListPayload.put("PropertyId", propertyId)
        }

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        getUnitTypeDataListPayload.put("systemParams", systemParams)
        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                Log.e("getPropertyMetaData", success.toString())
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!=null && success.size>=0) {

                        if (success[0] == null) {
                            if (success[1]!!.toString() != "{}") {
                                val jsonObject=JSONObject(success[1].toString())
                                 unitTypeList=jsonObject.getJSONArray("unitTypeList")
                                getUnitTypeDataDataListMutableData.postValue(unitTypeList)
                            } else {
                                getUnitTypeDataDataListMutableData.postValue(null)
                            }
                        }else{
                           // sessionExpiredDialog(activity!!)
                            Log.e("suuccckeep","=session expired")


                        }
                    }
                }
            }
        }, eventName, getUnitTypeDataListPayload, socket!!)

        return getUnitTypeDataDataListMutableData
    }
    //-------------
    fun getUpdateTaskStatus(eventName: String, statusTypeId: Int, taskId: Int, activity: FragmentActivity?): LiveData<Array<HouseKeepingListDataModel?>?> {
        val houseKeepingListMutableData = MutableLiveData<Array<HouseKeepingListDataModel?>?>()
        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        updateTaskStatusPayload.put("ModuleAction", "updateTaskStatus")
        updateTaskStatusPayload.put("completedByComments", null)
        updateTaskStatusPayload.put("statusTypeId", statusTypeId)
        updateTaskStatusPayload.put("taskId",taskId)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        updateTaskStatusPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!![0] == null) {
                        if (success[1]!!.toString() != "{}") {
                            Log.e("updateTaskStatus", success[0].toString())
                            Log.e("updateTaskStatus", success[1].toString())
                            activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_content, HouseKeepingFragment(),"HouseKeepingListFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).commit()
                        } else {
                            houseKeepingListMutableData.postValue(null)
                        }
                    }
                }
            }
        }, eventName, updateTaskStatusPayload, socket!!)
        return houseKeepingListMutableData
    }

    fun assignHousekeepersStatus(eventName: String, taskId: Int, listItems: JSONArray, activity: FragmentActivity?): LiveData<Array<HouseKeepingListDataModel?>?> {
        val houseKeepingListMutableData = MutableLiveData<Array<HouseKeepingListDataModel?>?>()
        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        assignHousekeepersPayload.put("ModuleAction", "assignHousekeepers")
        assignHousekeepersPayload.put("housekeepers", listItems)
        assignHousekeepersPayload.put("taskId",taskId)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        assignHousekeepersPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!=null) {
                        if (success[0] == null) {
                            if (success[1]!!.toString() != "{}") {
                                Log.e("assignHousekeeperStatus", success[0].toString())
                                Log.e("assignHousekeeperStatus", success[1].toString())
                                activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_content, HouseKeepingFragment(), "HouseKeepingListFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
                            } else {
                                houseKeepingListMutableData.postValue(null)
                            }
                        }
                    }else{

                    }
                }
            }
        }, eventName, assignHousekeepersPayload, socket!!)
        return houseKeepingListMutableData
    }

    fun getReservationDetailsSummary(eventName: String, ReservationId: Int): LiveData<JSONArray> {

        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        getReservationDetailsSummaryPayload.put("ModuleAction", "get_ReservationDetails_Summary")
        getReservationDetailsSummaryPayload.put("ReservationId", ReservationId)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        getReservationDetailsSummaryPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                Log.e("get_ReservationDetails", success.toString())
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!![0] == null) {
                        if (success[1]!!.toString() != "{}") {
                            Log.e("get_ReservationDetails", success[0].toString())
                            Log.e("get_ReservationDetails", success[1].toString())
                            arrayReservationDetail = JSONArray(success[1].toString())
                            reservationDetailMutableData.postValue(arrayReservationDetail)
                            } else {
                            reservationDetailMutableData.postValue(null)
                        }
                    }
                }
            }
        }, eventName, getReservationDetailsSummaryPayload, socket!!)
        return reservationDetailMutableData
    }

    fun getUpdateReservationStatus(eventName: String, statusId: Int, reservationId: Int, activity: FragmentActivity?): LiveData<JSONArray> {

        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        updateReservationStatusPayload.put("Ext_ReservationId", null)
        updateReservationStatusPayload.put("FunctionId", null)
        updateReservationStatusPayload.put("ReservationId", reservationId)
        updateReservationStatusPayload.put("StatusId",statusId)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        updateReservationStatusPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!![0] == null) {
                        if (success[1]!!.toString() != "{}") {
                            Log.e("ReservationStatus", success[0].toString())
                            Log.e("ReservationStatus", success[1].toString())
                            arrayReservationDetailStatus = JSONArray(success[1].toString())
                            var successMessage: String? = ""
                            if(arrayReservationDetailStatus!!.getJSONArray(0).getJSONObject(0).has("Success")){
                                successMessage = arrayReservationDetailStatus!!.getJSONArray(0).getJSONObject(0).getString("Success")
                            }else if(arrayReservationDetailStatus!!.getJSONArray(0).getJSONObject(0).has("SuccessMessage")){
                                successMessage = arrayReservationDetailStatus!!.getJSONArray(0).getJSONObject(0).getString("SuccessMessage")
                            }
                            if (successMessage != "") {
                                ReservationDetailFragment.newInstance(reservationId)
                              }
                            } else {
                            reservationStatusMutableData.postValue(null)
                        }
                    }
                }
            }
        }, eventName, updateReservationStatusPayload, socket!!)
        return reservationStatusMutableData
    }

    fun getGetFolioSummary(eventName: String,reservationId: Int): LiveData<JSONArray> {

        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }

        getFolioSummaryPayload.put("ModuleAction", "Get_FolioSummary")
        getFolioSummaryPayload.put("ReservationId", reservationId)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        getFolioSummaryPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!![0] == null) {
                        if (success[1]!!.toString() != "{}") {
                            Log.e("FolioSummary", success[0].toString())
                            Log.e("FolioSummary", success[1].toString())
                            arrayFolioSummary = JSONArray(success[1].toString())
                            folioSummaryMutableData.postValue(arrayFolioSummary)
                        } else {
                            folioSummaryMutableData.postValue(null)
                        }
                    }
                }
            }
        }, eventName, getFolioSummaryPayload, socket!!)
        return folioSummaryMutableData
    }

    //----Get Profile Details Data
    fun getProfileDetails(eventName: String ): LiveData<JSONArray> {
         val getProfileDetailsDataPayload = JSONObject()
        val getProfileDetailsDataMutableData = MutableLiveData<JSONArray>()
         val systemParams = JSONObject()
        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }
        systemParams.put("Source", "Android")
        systemParams.put("SourceId", StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        getProfileDetailsDataPayload.put("systemParams", systemParams)
        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                Log.e("getProfileDetailsData", success.toString())
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success != null && success.size >= 0) {

                        if (success!![0] == null) {
                            if (success[1]!!.toString() != "{}") {
                                Log.e("responceforpro", "==" + success[1].toString())
                                val jsonarrayprofile = JSONArray(success[1].toString())
                                getProfileDetailsDataMutableData.postValue(jsonarrayprofile)
                            } else {
                                getProfileDetailsDataMutableData.postValue(null)
                            }
                        }
                    }
                }
            }
                        }, eventName, getProfileDetailsDataPayload, socket!!)
                        return getProfileDetailsDataMutableData
                    }

    fun updateReservationStatus(eventName: String, statusId: Int, reservationId: Int,IsFromRU: Int, activity: FragmentActivity?){
        val updateReservationStatusFromRuPayload = JSONObject()
        val systemParams = JSONObject()
        if (socket == null) {
            val socketinstance = SingletonSocket()
            socket = socketinstance.instance
            socket?.connect()
        } else {
            socket?.connect()
        }
        updateReservationStatusFromRuPayload.put("IsFromRU", IsFromRU)
        updateReservationStatusFromRuPayload.put("ReservationId", reservationId)
        updateReservationStatusFromRuPayload.put("StatusId", statusId)

        systemParams.put("Source", "Android")
        systemParams.put("SourceId",  StaticConstants.sourceId)
        systemParams.put("token", StaticConstants.token)
        updateReservationStatusFromRuPayload.put("systemParams", systemParams)

        singletonSocketCall.getDataFromServer(object : OnSocketSuccessCallBack {
            override fun onSuccessDataReady(success: Array<out Any?>?) {
                val h = Handler(Looper.getMainLooper())
                h.post {
                    if (success!![0] == null) {
                        if (success[1]!!.toString() != "{}") {
                            Log.e("ReservationStatus", success[0].toString())
                            Log.e("ReservationStatus", success[1].toString())
                                activity!!.supportFragmentManager.beginTransaction().replace(R.id.main_content, ReservationListFragment(), "ReservationListFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).commit()
                        } else {
                            Toast.makeText(activity, "No Response", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }, eventName, updateReservationStatusFromRuPayload, socket!!)
    }
fun sessionExpiredDialog(activity: FragmentActivity) {
    val alertDialog = AlertDialog.Builder(activity, R.style.CustomAlertDialog)
    alertDialog.setIcon(R.drawable.ic_warning)
    alertDialog.setTitle("Alert !!!")
    alertDialog.setMessage("Session expired please logout?")
    alertDialog.setCancelable(false)
    alertDialog.setPositiveButton("OK") { _, _ ->
        SharedPreferenceService.destroyUserSession(activity!!)
        StaticConstants.url=""
        StaticConstants.sourceId=""
        var intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
       startActivity(activity!!,intent,null);
        activity!!.finish()

    }
    alertDialog.setNegativeButton("Cancel") { _, _ ->
        alertDialog.create().dismiss()

    }
    alertDialog.create().show()
}

}