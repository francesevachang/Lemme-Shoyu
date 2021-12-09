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
        supportActionBar?.elevation = 0F
        NavBarHandler(this, "Search")
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        tabLayout.getTabAt(1)!!.select()

        // TODO: delete if slider is not needed
        val slider = findViewById<RangeSlider>(R.id.slider_calories)
        slider.addOnChangeListener { slider, value, fromUser ->
            // Responds to when slider's value is changed
            val minMaxVal = slider.values
            findViewById<TextView>(R.id.slide_min_value).text = round(minMaxVal[0]).toString()
            findViewById<TextView>(R.id.slide_max_value).text = round(minMaxVal[1]).toString()
        }

        // TODO code starts here

    }


}