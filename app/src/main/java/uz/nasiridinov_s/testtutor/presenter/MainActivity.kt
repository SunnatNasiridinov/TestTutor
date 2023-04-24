package uz.nasiridinov_s.testtutor.presenter

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.nasiridinov_s.testtutor.AlarmReceiver
import uz.nasiridinov_s.testtutor.R
import uz.nasiridinov_s.testtutor.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::bind)
    private var alarmManager: AlarmManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotification()

        binding.set.setOnClickListener {
            val h = binding.timePicker.hour
            val m = binding.timePicker.minute
            Log.i("TTTTTTTTTTT", "$h: $m")

            // Set the alarm to start at approximately 2:00 p.m.
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.MONTH,4)
                set(Calendar.HOUR_OF_DAY, h)
                set(Calendar.MINUTE, m)
                set(Calendar.SECOND,0)
                set(Calendar.MILLISECOND,0)
            }

            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?
            val intent = Intent(this, AlarmReceiver::class.java)
            val pending = PendingIntent.getBroadcast(
                this, 0, intent,
                PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pending
            )
            Toast.makeText(this, "Alarm Set", Toast.LENGTH_SHORT).show()
            Log.i("TTTTTTTTTTT", "$calendar")
        }

        binding.exit.setOnClickListener {
            val intent = Intent(this, AlarmReceiver::class.java)
            val pending = PendingIntent.getBroadcast(this, 0, intent, 0)

            if (alarmManager == null) {
                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            } else {
                alarmManager?.cancel(pending)
                Toast.makeText(this, "Alarm Cancel", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun createNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyNotification" as CharSequence
            val desc = "Channel for Alarm Manager"
            val imp = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("1", name, imp)
            channel.description = desc

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }


}