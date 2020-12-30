package id.ac.ui.cs.mobileprogramming.nathasyaeliora.today

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.util.*

class TimerService : Service() {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val timeRemaining = arrayOf(intent.getIntExtra("TimeValue", 0))
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val intent1local = Intent()
                intent1local.action = "Counter"
                timeRemaining[0]--
                NotificationUpdate(timeRemaining[0])
                if (timeRemaining[0] <= 0) {
                    timer.cancel()
                }
                intent1local.putExtra("TimeRemaining", timeRemaining[0])
                sendBroadcast(intent1local)
            }
        }, 0, 1000)
        return super.onStartCommand(intent, flags, startId)
    }

    fun NotificationUpdate(timeLeft: Int) {
        try {
            val notificationIntent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0)
            val notification = arrayOf(
                NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("My Stop Watch")
                    .setContentText("Time Remaing : $timeLeft")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentIntent(pendingIntent)
                    .build()
            )
            startForeground(1, notification[0])
            var notificationChannel: NotificationChannel? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel = NotificationChannel(
                    CHANNEL_ID,
                    "My Counter Service",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            }
            var notificationManager: NotificationManager? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                notificationManager = getSystemService(NotificationManager::class.java)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager!!.createNotificationChannel(notificationChannel!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        private const val CHANNEL_ID = "NotificationChannelID"
    }
}