/**
 * Pacy Wu: I wrote the SearchActivity class to inflate the action bar and add the navigation bar listener.
 **/

package edu.uw.peihsi5.lemmeshoyu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout


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