package edu.uw.peihsi5.lemmeshoyu

import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context

// Reference:
// I used the code in Shia G's reply on
// https://stackoverflow.com/questions/34517520/how-to-give-notifications-on-android-on-specific-time
// to construct this class
class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val intent1 = Intent(context, MyNewIntentService::class.java)
        context.startService(intent1)
    }
}