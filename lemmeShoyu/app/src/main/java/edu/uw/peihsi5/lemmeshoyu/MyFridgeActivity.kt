/**
 * Pacy Wu: I wrote the MyFridgeActivity to inflate the action bar and add the navigation bar listener.
 **/



package edu.uw.peihsi5.lemmeshoyu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout

class MyFridgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_fridge)

        // set the action bar elevation to zero
        supportActionBar?.elevation = 0F

        // add navigation bar listener
        NavBarHandler(this,"My Fridge")

        // the navigation bar light on the selected activity
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.getTabAt(2)!!.select()
    }
}