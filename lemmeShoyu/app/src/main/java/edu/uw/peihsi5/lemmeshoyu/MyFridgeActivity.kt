package edu.uw.peihsi5.lemmeshoyu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import edu.uw.peihsi5.lemmeshoyu.databinding.ActivityMyFridgeBinding

private lateinit var appBarConfiguration: AppBarConfiguration
private lateinit var binding: ActivityMyFridgeBinding

class MyFridgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_fridge)
        supportActionBar?.elevation = 0F
        NavBarHandler(this,"My Fridge")

        // code starts here
    }
}