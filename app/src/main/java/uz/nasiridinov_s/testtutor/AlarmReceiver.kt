package uz.nasiridinov_s.testtutor

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import uz.nasiridinov_s.testtutor.presenter.MainActivity


class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Log.i("TTTTTTTTTTT", "onReceive: ")
        val i = Intent(context,MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK / Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pending = PendingIntent.getActivity(context,0,i,PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        var builder = NotificationCompat.Builder(context,"1")
            .setSmallIcon(R.drawable.ic_chat)
            .setContentTitle("Test Tutor Title")
            .setContentText("Content Text")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pending)

        var notificationCompat = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        notificationCompat.notify(1,builder.build())
    }
}