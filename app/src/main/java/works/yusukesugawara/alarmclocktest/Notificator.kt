package works.yusukesugawara.alarmclocktest

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

object Notificator {
    const val notificationChannelId = "channel"

    fun notify(context: Context, identifier: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationChannel = NotificationChannel(notificationChannelId, "Alarm", NotificationManager.IMPORTANCE_HIGH)

            notificationManager.createNotificationChannel(notificationChannel)
        }

        val notification = NotificationCompat.Builder(context, notificationChannelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Alarm Id=$identifier")
                .build()

        NotificationManagerCompat.from(context)
                .notify(identifier, notification)
    }

}