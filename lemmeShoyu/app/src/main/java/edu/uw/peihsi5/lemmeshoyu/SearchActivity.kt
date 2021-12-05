package edu.uw.peihsi5.lemmeshoyu

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

private const val TAG: String = ".SearchActivity"

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.elevation = 0F
        NavBarHandler(this, "Search")

        // code starts here

    }


}