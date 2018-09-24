package com.voltuswave.roomtempo.fragments

import android.app.Dialog
import android.content.Context
import android.support.annotation.NonNull
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.ListView
import com.google.gson.Gson
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.AssignedUser
import com.voltuswave.roomtempo.models.UsersList
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import org.json.JSONArray
import org.json.JSONObject


class AssignedUsersDialog(@NonNull context: Context, taskId: Int, private var userList: List<UsersList>?, private var assignedUserList: List<AssignedUser>?, position: Int, activity: FragmentActivity?) : Dialog(context) {

    private var mAssignedUsersList: ListView? = null
    private var adapter: CustomAdapter? = null
    private var btnSave: Button? = null
    private var storedProcedureCalls = StoredProcedureCalls()
    private var modelUserList: UsersList? = null
    var gson = Gson()

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.assignedusers_dialog_layout)
        mAssignedUsersList = findViewById(R.id.assignedUsersList)
        btnSave = findViewById(R.id.btn_save)

        for (i in 0 until userList!!.size) {
            val modeluserList = userList!![i]
            modeluserList.isChecked = false
        }

        Outer@ for (index in 0 until userList!!.size) {
            modelUserList = userList!![index]
            Inner@ for (index2 in 0 until assignedUserList!!.size) {
                if (assignedUserList!![index2].userId == modelUserList!!.userId) {
                    modelUserList!!.isChecked = true
                    break@Inner
                } else {
                    modelUserList!!.isChecked = false
                }
            }
        }

        adapter = CustomAdapter(context, userList)
        mAssignedUsersList!!.adapter = adapter
        adapter?.notifyDataSetChanged()

        btnSave!!.setOnClickListener {
            val selectedItems = adapter!!.getSelectedUsers()
            val jsonArray = JSONArray()
            for (entry in selectedItems.entries) {
                jsonArray.put(JSONObject(gson.toJson(entry.value)))
            }
            Log.e("outputStrArr", jsonArray.toString())
            storedProcedureCalls.assignHousekeepersStatus("assignHousekeepers", taskId, jsonArray, activity)
            MyHouseKeepingRecyclerViewAdapter.mAssignedUsersDialog!!.dismiss()
        }
    }
}