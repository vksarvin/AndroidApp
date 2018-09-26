package com.voltuswave.roomtempo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.voltuswave.roomtempo.Interface.SessionExpired
import com.voltuswave.roomtempo.authentication.LoginActivity
import com.voltuswave.roomtempo.fragments.*
import com.voltuswave.roomtempo.services.SharedPreferenceService
import com.voltuswave.roomtempo.shared.crashlytics.LogUser
import com.voltuswave.roomtempo.utils.StaticConstants
import com.voltuswave.roomtempo.utils.StringConstants
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, UserHomeFragment.OnListFragmentInteractionListener,
        ReservationListFragment.OnReservationListFragmentInteractionListener ,
        HouseKeepingFragment.OnHouseKeepingListFragmentInteractionListener,
        ReservationDetailFragment.OnReservationDetailFragmentInteractionListener,
        HouseKeepingDetailFragment.OnFragmentInteractionListener{
    private var activity: Activity? = null
    private lateinit var mMainActivity: MainActivity
    private lateinit var userFirstName : TextView
    private lateinit var email : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize activity
        activity = this
        mMainActivity = this
     //   Fabric.with(this, Crashlytics())
        setSupportActionBar(toolbar)

        /*val networkData = ConnectivityLiveData(this)
        networkData.observe(this, Observer{ network->
            val networkResponse: Int? = network?.describeContents()
            Toast.makeText(this, networkResponse.toString() ,Toast.LENGTH_SHORT).show()
        })*/

        /**
         * Log User Credentials to Crashlytics
         */
       // LogUser.logUser(applicationContext)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)
        nav_view.removeHeaderView(nav_view.getHeaderView(0))
        val headerLayout = nav_view.inflateHeaderView(R.layout.nav_header_main)
        userFirstName = headerLayout.findViewById(R.id.user_name)
        email = headerLayout.findViewById(R.id.user_email)
        headerLayout.setOnClickListener {
            //   gotoProfile()
        }
        // Substitute values
        updateNavigationDrawerHeaderValues(applicationContext)

        // Set Homepage Fragment
        supportFragmentManager.beginTransaction().replace(R.id.main_content, UserHomeFragment(),"UserHomeFragment").commit()

    }

    /**
     * Function to update username and email for navigation drawer header
     */
    private fun updateNavigationDrawerHeaderValues(context: Context) {

        // Fetch and substitute values from Shared Preference Service
        userFirstName.text = SharedPreferenceService.getValue(context,StringConstants.key_userFirstName)
        email.text = SharedPreferenceService.getValue(context,StringConstants.key_email)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {

            val houseKeepingDetailFragment = this.supportFragmentManager.findFragmentByTag("HouseKeepingDetailFragment")
            val houseKeepingListFragment = this.supportFragmentManager.findFragmentByTag("HouseKeepingListFragment")
            val reservationDetailFragment = this.supportFragmentManager.findFragmentByTag("ReservationDetailFragment")
            val reservationListFragment = this.supportFragmentManager.findFragmentByTag("ReservationListFragment")
            val userHomeFragment = this.supportFragmentManager.findFragmentByTag("UserHomeFragment")
            val profileFragment = this.supportFragmentManager.findFragmentByTag("ProfileFragment")

            if (houseKeepingListFragment != null && houseKeepingListFragment.isVisible) {
                Log.e("FragmentVisible:", "houseKeepingListFragment is visible")
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                overridePendingTransition(R.animator.match_user_enter, R.animator.match_user_leave)
                startActivity(intent)
           }else if (reservationListFragment != null && reservationListFragment.isVisible) {
                Log.e("FragmentVisible:", "reservationListFragment is visible")
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                overridePendingTransition(R.animator.match_user_enter, R.animator.match_user_leave)
                startActivity(intent)
            }
            else if (profileFragment != null && profileFragment.isVisible) {
                Log.e("FragmentVisible:", "profileFragment is visible")
                val intent = Intent(this@MainActivity, MainActivity::class.java)
                overridePendingTransition(R.anim.down_from_top, R.anim.up_from_bottom)
                startActivity(intent)
            }
            else if (houseKeepingDetailFragment != null && houseKeepingDetailFragment.isVisible) {
                Log.e("FragmentVisible:", "houseKeepingDetailFragment is visible")
                supportFragmentManager.beginTransaction().replace(R.id.main_content, HouseKeepingFragment(),"HouseKeepingListFragment").addToBackStack(null).commit()
            }else if (reservationDetailFragment != null && reservationDetailFragment.isVisible) {
                Log.e("FragmentVisible:", "reservationDetailFragment is visible")
                supportFragmentManager.beginTransaction().replace(R.id.main_content, ReservationListFragment(),"ReservationListFragment").addToBackStack(null).commit()
            }
            else if (userHomeFragment != null && userHomeFragment.isVisible) {
                val alertDialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
                alertDialog.setIcon(R.drawable.ic_warning)
                alertDialog.setTitle("Alert !!!")
                alertDialog.setMessage("Are you sure you want to logout?")
                alertDialog.setCancelable(false)
                alertDialog.setPositiveButton("OK") { _, _ ->
                    SharedPreferenceService.destroyUserSession(applicationContext)
                    StaticConstants.url=""
                    StaticConstants.sourceId=""
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()

                }
                alertDialog.setNegativeButton("Cancel") { _, _ ->
                alertDialog.create().dismiss()

                }
                alertDialog.create().show()
            }
            else {
                val count = supportFragmentManager.backStackEntryCount

                if (count == 0) {
                    super.onBackPressed()
                    //additional code
                } else {
                    supportFragmentManager.popBackStack()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        menu.clear()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the home action
                val userHomeFragment = this.supportFragmentManager.findFragmentByTag("UserHomeFragment")
                if (userHomeFragment != null && userHomeFragment.isVisible  ){
                    if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                        drawer_layout.closeDrawer(GravityCompat.START)
                    }
                }else{
                    val intent = Intent(this@MainActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)
                    finish()
                }
            }
            R.id.nav_logout -> {
                // Handle the logout action
                SharedPreferenceService.destroyUserSession(applicationContext)
                StaticConstants.url=""
                StaticConstants.sourceId=""
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            R.id.nav_profile -> {
                this.supportFragmentManager.beginTransaction().replace(R.id.main_content, ProfileFragment(), "ProfileFragment").commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onListFragmentInteraction(item: String?) {
        if (item == "Reservation List"){
            if (isConnected(this)) {
                supportFragmentManager.beginTransaction().replace(R.id.main_content, ReservationListFragment(), "ReservationListFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
            }else{
                // Show a snack bar for undo option
                Snackbar.make(
                        main_content, // Parent view
                        R.string.offline_mode, // Message to show
                        Snackbar.LENGTH_INDEFINITE //
                ).setAction( // Set an action for snack bar
                        "Retry" // Action button text
                ) { // Action button click listener
                    // Do something when retry action button clicked
                    if (isConnected(this)){
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, ReservationListFragment(), "ReservationListFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
                    }else{
                        Snackbar.make(
                                main_content, // Parent view
                                R.string.offline_mode, // Message to show
                                Snackbar.LENGTH_INDEFINITE //
                        ).show()
                    }
                }.show() // Finally show the snack bar
            }
        }else{
            if (isConnected(this)) {
            supportFragmentManager.beginTransaction().replace(R.id.main_content, HouseKeepingFragment(),"HouseKeepingFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
            }else{
                // Show a snack bar for undo option
                Snackbar.make(
                        main_content, // Parent view
                        R.string.offline_mode, // Message to show
                        Snackbar.LENGTH_INDEFINITE //
                ).setAction( // Set an action for snack bar
                        "Retry" // Action button text
                ) { // Action button click listener
                    // Do something when retry action button clicked
                    if(isConnected(this)){
                        supportFragmentManager.beginTransaction().replace(R.id.main_content, HouseKeepingFragment(),"HouseKeepingFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
                    }else{
                        Snackbar.make(
                                main_content, // Parent view
                                R.string.offline_mode, // Message to show
                                Snackbar.LENGTH_INDEFINITE //
                        ).show()
                    }
                }.show() // Finally show the snack bar
            }
        }
    }

    override fun onReservationListFragmentInteraction(reservationId: Int?) {
        val bundle = Bundle()
        bundle.putInt("reservationId", reservationId!!)
        val reservationDetailFragment = ReservationDetailFragment()
        reservationDetailFragment.arguments = bundle
        if (isConnected(this)) {
        supportFragmentManager.beginTransaction().replace(R.id.main_content, reservationDetailFragment,"ReservationDetailFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
        }else{
            // Show a snack bar for undo option
            Snackbar.make(
                    main_content, // Parent view
                    R.string.offline_mode, // Message to show
                    Snackbar.LENGTH_INDEFINITE //
            ).setAction( // Set an action for snack bar
                    "Retry" // Action button text
            ) { // Action button click listener
                // Do something when retry action button clicked
                if (isConnected(this)) {
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, reservationDetailFragment,"ReservationDetailFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).addToBackStack(null).commit()
                }else{
                    Snackbar.make(
                            main_content, // Parent view
                            R.string.offline_mode, // Message to show
                            Snackbar.LENGTH_INDEFINITE //
                    ).show()
                }
            }.show() // Finally show the snack bar
        }
    }

    override fun onHouseKeepingListFragmentInteraction(reservationId: Int?, reservationStatus: String) {
        val bundle = Bundle()
        bundle.putInt("reservationId", reservationId!!)
        bundle.putString("reservationStatus", reservationStatus)
        val houseKeepingDetailFragment = HouseKeepingDetailFragment()
        houseKeepingDetailFragment.arguments = bundle
        if (isConnected(this)){
        supportFragmentManager.beginTransaction().replace(R.id.main_content, houseKeepingDetailFragment,"HouseKeepingDetailFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).commit()
        }else{
            // Show a snack bar for undo option
            Snackbar.make(
                    main_content, // Parent view
                    R.string.offline_mode, // Message to show
                    Snackbar.LENGTH_INDEFINITE //
            ).setAction( // Set an action for snack bar
                    "Retry" // Action button text
            ) { // Action button click listener
                // Do something when retry action button clicked
                if (isConnected(this)){
                    supportFragmentManager.beginTransaction().replace(R.id.main_content, houseKeepingDetailFragment,"HouseKeepingDetailFragment").setCustomAnimations(R.animator.match_user_enter, R.animator.match_user_leave).commit()
                }else{
                    Snackbar.make(
                            main_content, // Parent view
                            R.string.offline_mode, // Message to show
                            Snackbar.LENGTH_INDEFINITE //
                    ).show()
                }
            }.show() // Finally show the snack bar
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    private fun isConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null
    }



}
