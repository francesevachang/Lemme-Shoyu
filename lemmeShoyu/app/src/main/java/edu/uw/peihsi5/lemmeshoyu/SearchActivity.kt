package edu.uw.peihsi5.lemmeshoyu

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.material.slider.RangeSlider
import com.google.android.material.tabs.TabLayout
import java.lang.Math.round

private const val TAG: String = ".SearchActivity"

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        // set the action bar elevation to zero
        supportActionBar?.elevation = 0F

        // add navigation bar listener
        NavBarHandler(this, "Search")

        // the navigation bar light on the selected activity
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.getTabAt(1)!!.select()

    }


}