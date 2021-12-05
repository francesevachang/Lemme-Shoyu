package edu.uw.peihsi5.lemmeshoyu

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.widget.Button

class NavBarHandler (val activity: Activity, val currentActivity: String) {

    init {
        // addEventListener
        activity.findViewById<Button>(R.id.homeNavButton).setOnClickListener { this.navHome() }
        activity.findViewById<Button>(R.id.searchNavButton).setOnClickListener { this.navSearch() }
        activity.findViewById<Button>(R.id.myFridgeNavButton).setOnClickListener { this.navMyFridge() }
    }

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

    private fun navHome() {
        if (currentActivity != "Home") {
            val goToMyFridgeActivity = Intent(activity, MainActivity::class.java)

            try {
                activity.startActivity(goToMyFridgeActivity)
                activity.overridePendingTransition(0,0)

            } catch (e: ActivityNotFoundException) {
                // Define what your app should do if no activity can handle the intent.
            }
        }
    }

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