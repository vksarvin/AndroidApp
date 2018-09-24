package com.voltuswave.roomtempo.fragments

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
import android.widget.LinearLayout
import com.facebook.shimmer.ShimmerFrameLayout
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.models.ReservationListDataModel
import com.voltuswave.roomtempo.viewModels.ReservationListViewModel



/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ReservationListFragment.OnListFragmentInteractionListener] interface.
 */
class ReservationListFragment : Fragment() {

    private var reservationListViewModel: ReservationListViewModel? = null
    // TODO: Customize parameters
    private var columnCount = 1
    private var listener: OnReservationListFragmentInteractionListener? = null
    private var mShimmerViewContainer: ShimmerFrameLayout? = null
    private var mRecyclerViewContainer: RecyclerView? = null
    private var myReservationListRecyclerViewAdapter: MyReservationListRecyclerViewAdapter? = null
    private var mInternet: LinearLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("ReservationList", "On Create")
        retainInstance = true
        setHasOptionsMenu(true)
        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.e("ReservationList", "On Create View")
        val view = inflater.inflate(R.layout.fragment_reservationlist_list, container, false)
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container)
        mRecyclerViewContainer = view.findViewById(R.id.reservationList)
        mInternet = view.findViewById<View>(R.id.linearLayout22) as LinearLayout
        reservationListViewModel = ViewModelProviders.of(this).get(ReservationListViewModel::class.java)
        if(isConnected(context)) {
            reservationListViewModel!!.requestReservationsListFromModel("GetReservationsList",activity!!)
        }else{
            mInternet!!.visibility = View.VISIBLE
        }
        // Set the adapter
        reservationListViewModel!!.reservationlist.removeObservers(this)
        reservationListViewModel!!.reservationlist.observe(this, Observer { t: Array<ReservationListDataModel?>? ->
                if (t == null) {
                    mRecyclerViewContainer!!.visibility = View.GONE
                } else {
                    mRecyclerViewContainer!!.visibility = View.VISIBLE
                    Log.e("List of reservations", t[0]!!.reservationList!!.size.toString())
                    // Stopping Shimmer Effect's animation after data is loaded to ListView
                    mShimmerViewContainer!!.stopShimmerAnimation()
                    mShimmerViewContainer!!.visibility = View.GONE
                    if (mRecyclerViewContainer is RecyclerView) {
                        with(mRecyclerViewContainer) {
                            this!!.layoutManager = when {
                                columnCount <= 1 -> LinearLayoutManager(context)
                                else -> GridLayoutManager(context, columnCount)
                            }
                            myReservationListRecyclerViewAdapter = MyReservationListRecyclerViewAdapter(t[0]!!.reservationList!!,activity, listener)
                            adapter = myReservationListRecyclerViewAdapter
                            /*if (myReservationListRecyclerViewAdapter == null){
                                myReservationListRecyclerViewAdapter = MyReservationListRecyclerViewAdapter(t[0]!!.reservationList!!, listener)
                                adapter = myReservationListRecyclerViewAdapter
                            }else{
                                myReservationListRecyclerViewAdapter!!.updateList(t[0]!!.reservationList!!)
                                adapter = myReservationListRecyclerViewAdapter
                            }*/
                        }
                    }
                }
            })

        return view
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
                myReservationListRecyclerViewAdapter!!.filter(query)
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
        if (context is OnReservationListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
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
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnReservationListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onReservationListFragmentInteraction(reservationId: Int?)
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ReservationListFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        Log.e("ReservationList", "On Start")
        activity!!.title = "Reservation List"
    }

    override fun onResume() {
        super.onResume()
        if(!isConnected(context)){
            mInternet!!.visibility = View.VISIBLE
        }else{
            mInternet!!.visibility = View.GONE
        }
        Log.e("ReservationList", "On Resume")
        mShimmerViewContainer!!.startShimmerAnimation()
    }

    override fun onPause() {
        Log.e("ReservationList", "On Pause")
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
