/**
 * Pacy Wu: I wrote the class NavBarHandler, which add onClickListener to each item
 * in the navigation bar, so the user is able to navigation between activities.
 **/

package edu.uw.peihsi5.lemmeshoyu

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.material.tabs.TabLayout

private const val TAG = ".NavBarHandler"

class NavBarHandler (val activity: Activity, val currentActivity: String) {

    init {
        // addEventListener
        val tabLayout = activity.findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
                var position: Int = tab!!.position
                if (position == 0) { // home page
                    navHome()
                } else if (position == 1) { // search page
                    navSearch()
                } else if (position == 2) { // my fridge page
                    navMyFridge()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // nothing to do here
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // nothing to do here
            }
        })
    }

    /** Navigate to the search activity **/
    private fun navSearch () {
        if (currentActivity != "Search") {
            val goToSearchActivity = Intent(activity, SearchActivity::class.java)
            try {
                activity.startActivity(goToSearchActivity)
                activity.overridePendingTransition(0,0)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
            }
        }
    }

    /** Navigate to home  **/
    private fun navHome() {
        if (currentActivity != "Home") {
            val goToHomeActivity = Intent(activity, MainActivity::class.java)

            try {
                activity.startActivity(goToHomeActivity)
                activity.overridePendingTransition(0,0)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
            }
        }
    }

    /** Navigate to my fridge activity **/
    private fun navMyFridge() {
        if (currentActivity != "My Fridge") {
            val goToMyFridgeActivity = Intent(activity, MyFridgeActivity::class.java)

            try {
                activity.startActivity(goToMyFridgeActivity)
                activity.overridePendingTransition(0,0)
            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
            }
        }
    }
}