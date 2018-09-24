package com.voltuswave.roomtempo.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.voltuswave.roomtempo.R
import com.voltuswave.roomtempo.services.SharedPreferenceService


/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [UserHomeFragment.OnListFragmentInteractionListener] interface.
 */
class UserHomeFragment : Fragment(){

    // TODO: Customize parameters
    private var columnCount = 2

    private var listener: OnListFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_homemenu_list, container, false)

        SharedPreferenceService.removeValue(context!!,"selectedDate")
        SharedPreferenceService.removeValue(context!!,"propertyId")
        SharedPreferenceService.removeValue(context!!,"unitTypeId")

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                val items = ArrayList<String>()
                items.add("Reservation List")
                items.add("House Keeping")
                adapter = MyHomeMenuRecyclerViewAdapter(items,context, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
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
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: String?)
    }

    override fun onStart() {
        super.onStart()
        activity!!.title = "Host Tempo"
    }

    override fun onResume() {
        super.onResume()
        if(!isConnected(context)){
            Toast.makeText(context,R.string.offline_mode,Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        if(!isConnected(context)){
            Toast.makeText(context,R.string.offline_mode,Toast.LENGTH_SHORT).show()
        }
    }

    private fun isConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null
    }

}
