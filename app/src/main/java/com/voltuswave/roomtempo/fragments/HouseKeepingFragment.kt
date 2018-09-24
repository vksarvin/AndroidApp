package com.voltuswave.roomtempo.fragments


import android.app.DatePickerDialog
import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.HouseKeepingListDataModel
import com.voltuswave.roomtempo.models.UnitDetail_
import com.voltuswave.roomtempo.models.UsersList
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import com.voltuswave.roomtempo.services.SharedPreferenceService
import com.voltuswave.roomtempo.viewModels.GetPropertyListViewModel
import com.voltuswave.roomtempo.viewModels.GetUnitTypeListViewModel
import com.voltuswave.roomtempo.viewModels.HouseKeepingListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [HouseKeepingFragment.OnHouseKeepingListFragmentInteractionListener] interface.
 */
class HouseKeepingFragment : Fragment() {

    private var houseKeepingListViewModel: HouseKeepingListViewModel? = null
    private var getPropertyListViewModel: GetPropertyListViewModel? = null
    private var getUnitTypeListViewModel: GetUnitTypeListViewModel? = null
    private val selectUnitTypeDataModelArrayList = mutableListOf<String>()
    private val selectPropertyDataModelArrayList = mutableListOf<String>()
    private val selectPropertyIdArrayList = mutableListOf<Int>()
    private val selectedUnitTypeIdArrayList = mutableListOf<Int>()
    private val houseKeepingList = mutableListOf<UnitDetail_>()
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    private var mRecyclerViewContainer: RecyclerView? = null
    private var mInternet: LinearLayout? = null
    var storedProcedureCalls = StoredProcedureCalls()
    private var myHouseKeepingRecyclerViewAdapter: MyHouseKeepingRecyclerViewAdapter? = null
    // TODO: Customize parameters
    private var columnCount = 1
    private var listener: OnHouseKeepingListFragmentInteractionListener? = null
    private var usersList: List<UsersList>? = null
    private var houseKeepingArrayMap: MutableMap<String, UnitDetail_?> = mutableMapOf()
    private var unitDetailsObject: Any? = null
    private var propertyId = 0
    private var unitTypeId = 0
    var propertyIdForApplyFilter = 0
    var unitTypeIdForApplyFilter = 0
    private var innerView: View? = null
    private var dateObjValueForApplyFilter: String? = null
    private val cal = Calendar.getInstance()
    private val payloadFormat = "yyyy-MM-dd" // mention the format you need
    private val displayFormat = "dd MMM yyyy"
    private val sdf = SimpleDateFormat(payloadFormat, Locale.US)
    private val sdfDisplay = SimpleDateFormat(displayFormat, Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("HouseKeepingFragment", "onCreate called")
        retainInstance = true
        setHasOptionsMenu(true)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_housekeeping_list, container, false)
        this.innerView = view
        Log.e("HouseKeepingFragment", "onCreateView called")
        mRecyclerViewContainer=view.findViewById(R.id.houseKeepingList)
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)

        val dateFilter = view.findViewById<Button>(R.id.select_date)
        val applyFilter = view.findViewById<Button>(R.id.btn_apply_filter)
        mInternet = view.findViewById<View>(R.id.linearLayout22) as LinearLayout
        dateFilter!!.text = SharedPreferenceService.getDateValue(context!!,"selectedDate")!!
        dateObjValueForApplyFilter = sdf.format(cal.time)
        val pId = SharedPreferenceService.getPropertyIdValue(context!!,"propertyId")
        propertyId = pId!!.toInt()
        val uId = SharedPreferenceService.getUnitTypeIdValue(context!!,"unitTypeId")
        unitTypeId = uId!!.toInt()
        getPropertyMetaData()
        getUnitTypesListBySearchText("GetUnitTypesListBySearchText",0,null)
        getHouseKeepingList("getHouseKeepinglist", SharedPreferenceService.getDateValue(context!!,"selectedDate")!!, propertyId, unitTypeId)

        dateFilter.setOnClickListener {
            val mYear = cal.get(Calendar.YEAR)
            val mMonth = cal.get(Calendar.MONTH)
            val mDay = cal.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(context!!,R.style.CalenderDialogTheme,
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        cal.set(Calendar.YEAR, year)
                        cal.set(Calendar.MONTH, monthOfYear)
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                        val payloadFormat = "yyyy-MM-dd" // mention the format you need
                        val displayFormat = "dd MMM yyyy"
                        val sdf = SimpleDateFormat(payloadFormat, Locale.US)
                        val sdfDisplay = SimpleDateFormat(displayFormat, Locale.US)
                        dateFilter.text = sdfDisplay.format(cal.time)
                        dateObjValueForApplyFilter = sdf.format(cal.time)
                        SharedPreferenceService.storeUserDetails(context!!,"selectedDate", dateObjValueForApplyFilter!!)
                        if (isConnected(context)) {
                            getHouseKeepingList("getHouseKeepinglist", SharedPreferenceService.getDateValue(context!!,"selectedDate")!!, propertyIdForApplyFilter, unitTypeIdForApplyFilter)
                        }else{
                            mInternet!!.visibility = View.VISIBLE
                            Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
                        }
                    }, mYear, mMonth, mDay)
            datePickerDialog.show()
        }

        applyFilter.setOnClickListener {

            if (isConnected(context)) {
                getHouseKeepingList("getHouseKeepinglist", SharedPreferenceService.getDateValue(context!!,"selectedDate")!!, propertyIdForApplyFilter, unitTypeIdForApplyFilter)
            }else{
                mInternet!!.visibility = View.VISIBLE
                Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.e("HouseKeepingFragment", "onActivityCreated called")
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            dateObjValueForApplyFilter = savedInstanceState.getString("dateChoice", sdf.format(cal.time))
            propertyIdForApplyFilter = savedInstanceState.getInt("propertyIdChoice",0)
            unitTypeIdForApplyFilter = savedInstanceState.getInt("unitIdChoice",0)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("HouseKeepingFragment", "onSaveInstanceState called")
        outState.putString("dateChoice", dateObjValueForApplyFilter)
        outState.putInt("propertyIdChoice", propertyIdForApplyFilter)
        outState.putInt("unitIdChoice", unitTypeIdForApplyFilter)
    }

    private fun getPropertyMetaData(){
        if (isConnected(context)){
            val propertyIdSpinner = innerView?.findViewById<Spinner>(R.id.propertyId)
            getPropertyListViewModel = ViewModelProviders.of(this).get(GetPropertyListViewModel::class.java)
            getPropertyListViewModel!!.requestgetpropertyListFromModel("GetPropertyMetaData",activity!!)
            getPropertyListViewModel!!.getpropertylist.removeObservers(this)
            getPropertyListViewModel!!.getpropertylist.observe(activity!!, Observer { t:JSONArray?->
                if (t!=null && t.length()>0){
                    for (index in 0 until t.length()) {
                        val propertyJsonObject=t[index] as JSONObject
                        val propertyName=propertyJsonObject.getString("PropertyName")
                        val propertyId=propertyJsonObject.getInt("PropertyId")
                        selectPropertyDataModelArrayList.add(propertyName)
                        selectPropertyIdArrayList.add(propertyId)
                    }
                } else {
                    Toast.makeText(activity!!,"Empty List",Toast.LENGTH_SHORT).show()
                }
            })
            val propertyDataModelArrayAdapter = SpinnerAdapter(context, R.layout.spinner_item, selectPropertyDataModelArrayList, "Select Property" )
            propertyIdSpinner?.adapter = propertyDataModelArrayAdapter
            var selectedPropertyId: Int?
            propertyIdSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    if (position > 0) {
                        selectedUnitTypeIdArrayList.clear()
                        selectUnitTypeDataModelArrayList.clear()
                        selectedPropertyId = selectPropertyIdArrayList[position - 1]
                        propertyIdForApplyFilter = selectedPropertyId!!
                        SharedPreferenceService.storeUserDetails(context!!,"propertyId", propertyIdForApplyFilter.toString())
                        SharedPreferenceService.removeValue(context!!,"unitTypeId")
                        getUnitTypesListBySearchText("GetUnitTypesListBySearchText", selectedPropertyId!!, null)
                    }else{
                        propertyIdForApplyFilter = 0
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }else{
            mInternet!!.visibility = View.VISIBLE
            Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUnitTypesListBySearchText(eventName: String,propertyId: Int ,searchText: String?){
        if(isConnected(context)){
            val unitTypeIdSpinner = innerView?.findViewById<Spinner>(R.id.unitTypeId)
            getUnitTypeListViewModel=ViewModelProviders.of(this).get(GetUnitTypeListViewModel::class.java)
            getUnitTypeListViewModel!!.requestGetUnitTypeListFromModel(eventName,propertyId,searchText,activity!!)
            getUnitTypeListViewModel!!.getUnitTypeList.removeObservers(this)
            getUnitTypeListViewModel!!.getUnitTypeList.observe(activity!!, Observer { t: JSONArray? ->
                if (t!=null && t.length()>0){
                    selectedUnitTypeIdArrayList.clear()
                    selectUnitTypeDataModelArrayList.clear()
                    val unitTypeDataModelArrayAdapter = SpinnerAdapter(context, R.layout.spinner_item, selectUnitTypeDataModelArrayList, "Select UnitType" )
                    unitTypeIdSpinner?.adapter = unitTypeDataModelArrayAdapter
                    for (index in 0 until t.length()) {
                        val jsonObject=t[index] as JSONObject
                        val unitTypeName=jsonObject.getString("UnitTypeName")
                        val unitTypeId=jsonObject.getInt("UnitTypeId")
                        selectedUnitTypeIdArrayList.add(unitTypeId)
                        selectUnitTypeDataModelArrayList.add(unitTypeName)
                    }
                } else {
                    Toast.makeText(activity!!,"Empty List",Toast.LENGTH_SHORT).show()
                }
            })
            val unitTypeDataModelArrayAdapter = SpinnerAdapter(context, R.layout.spinner_item, selectUnitTypeDataModelArrayList, "Select UnitType" )
            unitTypeIdSpinner?.adapter = unitTypeDataModelArrayAdapter
            var selectedUnitTypeId:Int ?
            unitTypeIdSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    if (position > 0) {
                        selectedUnitTypeId = selectedUnitTypeIdArrayList[position - 1]
                        unitTypeIdForApplyFilter = selectedUnitTypeId!!
                        SharedPreferenceService.storeUserDetails(context!!,"unitTypeId", unitTypeIdForApplyFilter.toString())
                    }else{
                        unitTypeIdForApplyFilter = 0
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }else{
            mInternet!!.visibility = View.VISIBLE
            Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getHouseKeepingList(eventName: String, dateObject:String, propertyId:Int, unitTypeId:Int) {
        if (isConnected(context)) {
            houseKeepingListViewModel = ViewModelProviders.of(this).get(HouseKeepingListViewModel::class.java)
            houseKeepingListViewModel!!.requestHouseKeepingListFromModel(eventName, dateObject, propertyId, unitTypeId,activity!!)
            // Set the adapter
            houseKeepingListViewModel!!.housekeepinglist.removeObservers(this)
            houseKeepingListViewModel!!.housekeepinglist.observe(this, Observer { t: Array<HouseKeepingListDataModel?>? ->
                if (t == null) {
                    mRecyclerViewContainer!!.visibility = View.GONE
                    Toast.makeText(activity!!, "No Data Found", Toast.LENGTH_SHORT).show()
                } else {
                    if (t.isNotEmpty()) {
                        mRecyclerViewContainer!!.visibility = View.VISIBLE
                        // Stopping Shimmer Effect's animation after data is loaded to ListView
                        mShimmerViewContainer!!.stopShimmerAnimation()
                        mShimmerViewContainer!!.visibility = View.GONE
                        houseKeepingList.clear()
                        usersList = t[0]!!.usersList
                        unitDetailsObject = t[0]!!.uniqueUnitDetail
                        val houseKeepingArray: MutableMap<String, UnitDetail_?> = mutableMapOf()
                        val keyList: MutableList<String?> = mutableListOf()
                        val gson = Gson()
                        val jsonString: String? = gson.toJson(unitDetailsObject)
                        for (key in JSONObject(jsonString).keys()) {
                            Log.e("HouseKeeping_key", key)
                            Log.e("HouseKeeping_value", JSONObject(jsonString).getJSONObject(key).toString())
                            val parser = JsonParser()
                            val mJson = parser.parse(JSONObject(jsonString).getJSONObject(key).toString())
                            val unitDetail: UnitDetail_ = gson.fromJson<UnitDetail_>(mJson, UnitDetail_::class.java)
                            houseKeepingArray[key] = unitDetail
                            keyList.add(key)
                        }
                        houseKeepingArrayMap = houseKeepingArray
                        for (i: Int in 0 until keyList.size){
                            val key = keyList[i]
                            houseKeepingList.add(i, houseKeepingArrayMap[key]!!)
                        }

                        if (mRecyclerViewContainer is RecyclerView) {
                            with(mRecyclerViewContainer) {
                                this!!.layoutManager = when {
                                    columnCount <= 1 -> LinearLayoutManager(context)
                                    else -> GridLayoutManager(context, columnCount)
                                }
                                myHouseKeepingRecyclerViewAdapter = MyHouseKeepingRecyclerViewAdapter(context, activity,houseKeepingList, usersList, listener)
                                adapter = myHouseKeepingRecyclerViewAdapter
                            }
                        }
                        /*if (t[0]!!.uniqueUnitDetail.toString() == "{}") {
                            Toast.makeText(activity!!, "No Data Found", Toast.LENGTH_SHORT).show()
                        }*/
                    }

                }
            })
        } else {
            mInternet!!.visibility = View.VISIBLE
            Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater!!.inflate(R.menu.main, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        val mSearchMenuItem = menu!!.findItem(R.id.action_search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        var searchView: SearchView? = null
        if (mSearchMenuItem != null) {
            searchView = mSearchMenuItem.actionView as SearchView?
        }
        searchView?.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                Log.e("Search filter", query)
                myHouseKeepingRecyclerViewAdapter!!.filter(query)
                return true
            }
        })


        searchView.setOnSearchClickListener {
            Log.e("Search", "Search Expands")
        }

        searchView.setOnCloseListener {
            Log.e("Search", "Search Collapse")
            false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.e("HouseKeepingFragment", "onAttach called")
        if (context is OnHouseKeepingListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnHouseKeepingListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.e("HouseKeepingFragment", "onDetach called")
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnHouseKeepingListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onHouseKeepingListFragmentInteraction(reservationId: Int?, reservationStatus: String)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                HouseKeepingFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        Log.e("HouseKeepingFragment", "onStart called")
        activity!!.title = "HouseKeeping"
    }

    override fun onResume() {
        super.onResume()
        Log.e("HouseKeepingFragment", "onResume called")
        if(!isConnected(context)){
            mInternet!!.visibility = View.VISIBLE
        }else{
            mInternet!!.visibility = View.GONE
        }
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
        Log.e("HouseKeepingFragment", "onPause called")
        if(!isConnected(context)){
            mInternet!!.visibility = View.VISIBLE
        }else{
            mInternet!!.visibility = View.GONE
        }
        mShimmerViewContainer!!.stopShimmerAnimation()
    }

    private fun isConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null
    }
}
