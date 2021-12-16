package edu.uw.peihsi5.lemmeshoyu

import androidx.core.app.NotificationManagerCompat
import android.app.PendingIntent
import android.content.Intent
import android.R
import android.app.IntentService
import android.app.Notification

// Reference:
// I used the code in Shia G's reply on
// https://stackoverflow.com/questions/34517520/how-to-give-notifications-on-android-on-specific-time
// to construct this class
class MyNewIntentService : IntentService("MyNewIntentService") {
    override fun onHandleIntent(intent: Intent?) {
        val builder: Notification.Builder = Notification.Builder(this)
        builder.setContentTitle("My Title")
        builder.setContentText("This is the Body")
        // builder.setSmallIcon(R.drawable.whatever)
        val notifyIntent = Intent(this, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent)
        val notificationCompat: Notification = builder.build()
        val managerCompat = NotificationManagerCompat.from(this)
        managerCompat.notify(NOTIFICATION_ID, notificationCompat)
    }

    companion object {
        private const val NOTIFICATION_ID = 3
    }
}