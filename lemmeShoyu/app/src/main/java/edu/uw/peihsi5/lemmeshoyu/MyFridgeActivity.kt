package edu.uw.peihsi5.lemmeshoyu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.tabs.TabLayout
import edu.uw.peihsi5.lemmeshoyu.databinding.ActivityMyFridgeBinding

private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMyFridgeBinding

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