package com.voltuswave.roomtempo.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.*
import com.voltuswave.roomtempo.network.StoredProcedureCalls
import com.voltuswave.roomtempo.viewModels.FolioDetailViewModel
import com.voltuswave.roomtempo.viewModels.HouseKeepingDetailViewModel
import org.json.JSONArray

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "reservationId"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ReservationDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ReservationDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ReservationDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int = 0
    private var listener: OnReservationDetailFragmentInteractionListener? = null
    private var houseKeepingDetailViewModel: HouseKeepingDetailViewModel? = null
    private var folioSummaryDetailViewModel: FolioDetailViewModel? = null

    private var statuslist: Array<ReservationDetailStatusModel>? = null
    private var reservationDetailsObject: Array<ReservationDetailModel>? = null
    private var folioitemlist: Array<FolioItem>? = null
    private var ledgeritemlist: Array<LedgerItem>? = null
    private var ledgeraccountlist: Array<LedgerAccountItem>? = null
    private var totalfolioitem: Array<TotalFolioItem>? = null
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    var statusDataModelArrayList = mutableListOf<String>()
    var folioSummaryArrayList = mutableListOf<FolioItem>()
    var storedProcedureCalls = StoredProcedureCalls()
    private var mInternet: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reservation_detail, container, false)
        val reservationdetail = view.findViewById<LinearLayout>(R.id.reservation_detail)
        val foliodetail = view.findViewById<LinearLayout>(R.id.folio_detail)
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        val guest_full_name = view.findViewById<TextView>(R.id.guest_full_name)
        val persons_count = view.findViewById<TextView>(R.id.persons_count)
        val iv_mail = view.findViewById<ImageView>(R.id.iv_mail)
        val iv_call = view.findViewById<ImageView>(R.id.iv_call)
        val arrival_date = view.findViewById<TextView>(R.id.arrival_date)
        val nights = view.findViewById<TextView>(R.id.nights)
        val departure_date = view.findViewById<TextView>(R.id.departure_date)
        val unittypename_label = view.findViewById<TextView>(R.id.unittypename_label)
        val propertynickname = view.findViewById<TextView>(R.id.propertynickname)
        val unitnickname = view.findViewById<TextView>(R.id.unitnickname)
        val status_spinner = view.findViewById<Spinner>(R.id.reservationStatus)
        mInternet = view.findViewById<View>(R.id.linearLayout22) as LinearLayout

        val balance_value = view.findViewById<TextView>(R.id.balance_value)
        val rv_foliopaymentsummary = view.findViewById<RecyclerView>(R.id.rv_foliopaymentsummary)
        val tax_value = view.findViewById<TextView>(R.id.tax_value)
        val total_value = view.findViewById<TextView>(R.id.total_value)
        val payments_value = view.findViewById<TextView>(R.id.payments_value)

        houseKeepingDetailViewModel = ViewModelProviders.of(this).get(HouseKeepingDetailViewModel::class.java)
        folioSummaryDetailViewModel = ViewModelProviders.of(this).get(FolioDetailViewModel::class.java)

        if (isConnected(context)) {
            houseKeepingDetailViewModel!!.requestHouseKeepingDetailFromModel("get_ReservationDetails_Summary", param1)
            folioSummaryDetailViewModel!!.requestFolioDetailFromModel("Get_FolioSummary", param1)
        }else{
            mInternet!!.visibility = View.VISIBLE
            Toast.makeText(context, R.string.offline_mode, Toast.LENGTH_SHORT).show()
        }
        houseKeepingDetailViewModel!!.housekeepingdetail.removeObservers(this)
        folioSummaryDetailViewModel!!.folioSummarydetail.removeObservers(this)
        houseKeepingDetailViewModel!!.housekeepingdetail.observe(this, Observer { t: JSONArray? ->
            if(t == null){
                reservationdetail!!.visibility = View.GONE
                Toast.makeText(context,"No Data",Toast.LENGTH_SHORT).show()
            }else{
                reservationdetail!!.visibility = View.VISIBLE
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer!!.stopShimmerAnimation()
                mShimmerViewContainer!!.visibility = View.GONE
                val errorMessage = t.getJSONArray(0).getJSONObject(0).getString("ErrorMessage")
                val statusResponse = t.getJSONArray(1)
                val jsonStatusResponse = statusResponse.toString()
                val gson = Gson()
                statuslist = gson.fromJson(jsonStatusResponse, Array<ReservationDetailStatusModel>::class.java)
                Log.e("statusresponse",statusResponse.toString())
                val reservationDetailResponse = t.getJSONArray(2)
                Log.e("reservationdetail", reservationDetailResponse.toString())
                val jsonStr = reservationDetailResponse.toString()
                reservationDetailsObject = gson.fromJson(jsonStr, Array<ReservationDetailModel>::class.java)
                guest_full_name.text = reservationDetailsObject!![0].guestFullName
                persons_count.text = reservationDetailsObject!![0].persons.toString()
                arrival_date.text = reservationDetailsObject!![0].arrivalDate
                nights.text = "${reservationDetailsObject!![0].nights} Nights"
                departure_date.text = reservationDetailsObject!![0].departureDate
                unittypename_label.text = reservationDetailsObject!![0].unitTypeName
                unitnickname.text = reservationDetailsObject!![0].unitNickName
                propertynickname.text = reservationDetailsObject!![0].propertyNickName
                val statusId = reservationDetailsObject!![0].statusId
                val reservationId = reservationDetailsObject!![0].reservationId
                if(statuslist != null){
                    for (i in 0 until statuslist!!.size) {
                        statusDataModelArrayList.add(statuslist!![i].statusName)
                    }

                    val reservationStatusArrayAdapter = SpinnerAdapter(context, R.layout.spinner_item, statusDataModelArrayList, reservationDetailsObject!![0].statusName )
                    status_spinner.adapter = reservationStatusArrayAdapter
                    status_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

                        override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                            if (position > 0) {
                                if (isConnected(context)) {
                                    storedProcedureCalls.getUpdateReservationStatus("UpdateReservationStatus", statuslist!![position - 1].statusId, reservationId, activity)
                                }else{
                                    mInternet!!.visibility = View.VISIBLE
                                }
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {

                        }
                    }
                }


            }


        })

        folioSummaryDetailViewModel!!.folioSummarydetail.observe(this, Observer { t: JSONArray? ->
            if(t == null){
                foliodetail!!.visibility = View.GONE
                Toast.makeText(context,"No Data", Toast.LENGTH_SHORT).show()
            }else {
                foliodetail!!.visibility = View.VISIBLE
                // Stopping Shimmer Effect's animation after data is loaded to ListView
                mShimmerViewContainer!!.stopShimmerAnimation()
                mShimmerViewContainer!!.visibility = View.GONE
                val jsonArrayResponse = t
                val folioItemResponse = t.getJSONArray(0)
                val jsonFolioItemResponse = folioItemResponse.toString()
                val gson = Gson()
                folioitemlist = gson.fromJson(jsonFolioItemResponse, Array<FolioItem>::class.java)
                val ledgerItemResponse = t.getJSONArray(1)
                val jsonledgerItemResponse = ledgerItemResponse.toString()
                ledgeritemlist = gson.fromJson(jsonledgerItemResponse, Array<LedgerItem>::class.java)
                val ledgerAccountItemResponse = t.getJSONArray(3)
                val jsonledgerItemAccountResponse = ledgerAccountItemResponse.toString()
                ledgeraccountlist = gson.fromJson(jsonledgerItemAccountResponse, Array<LedgerAccountItem>::class.java)
                val totalfolioItemResponse = t.getJSONArray(2)
                val jsonTotalFolioItemResponse = totalfolioItemResponse.toString()
                totalfolioitem = gson.fromJson(jsonTotalFolioItemResponse, Array<TotalFolioItem>::class.java)
                balance_value.text = "$ ${totalfolioitem!![0].balance}"
                if(folioitemlist != null){
                    for (i in 0 until folioitemlist!!.size) {
                        folioSummaryArrayList.add(folioitemlist!![i])
                    }
                    rv_foliopaymentsummary.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    rv_foliopaymentsummary.adapter = FolioAccountAdapter(folioSummaryArrayList)
                }
                tax_value.text = "$ ${totalfolioitem!![0].totalTax}"
                total_value.text = "$ ${totalfolioitem!![0].totalValue}"
                payments_value.text = "$ ${totalfolioitem!![0].payments}"

            }
        })

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnReservationDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnReservationDetailFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReservationDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
                ReservationDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        activity!!.title = "Reservation Detail"
    }

    override fun onResume() {
        super.onResume()
        if(!isConnected(context)){
            mInternet!!.visibility = View.VISIBLE
        }else{
            mInternet!!.visibility = View.GONE
        }
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        super.onPause()
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
