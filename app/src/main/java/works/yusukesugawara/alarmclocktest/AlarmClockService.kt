package works.yusukesugawara.alarmclocktest

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

class AlarmClockService: Service() {
    companion object {
        const val REQUEST_TYPE = "REQUEST_TYPE"
        const val REQUEST_TYPE_SET_NEXT_ALARM_CLOCK = "REQUEST_TYPE_SET_NEXT_ALARM_CLOCK"

        const val IDENTIFIER = "IDENTIFIER"

        fun setNextAlarmClock(context: Context, identifier: Int) {
            val currentEpochTime = System.currentTimeMillis()

            val laterEpochTime = currentEpochTime.addMinute(1)


            Log.d(AlarmClockService::class.java.simpleName, "setNextAlarmClock: currentEpochTime=${currentEpochTime.toHumanReadableString()}, laterEpochTime=${laterEpochTime.toHumanReadableString()}")


            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val alarmClockInfo = AlarmManager.AlarmClockInfo(laterEpochTime, null)
            val intent = Intent(context, AlarmClockService::class.java).also {
                it.putExtra(REQUEST_TYPE, REQUEST_TYPE_SET_NEXT_ALARM_CLOCK)
                it.putExtra(IDENTIFIER, identifier)
            }
            val operation = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setAlarmClock(alarmClockInfo, operation)
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(javaClass.simpleName, "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(javaClass.simpleName, "onStartCommand")

        run {
            val extras = intent?.extras ?: return@run
            val requestType = extras.getString(REQUEST_TYPE) ?: return@run
            val identifier = extras.getInt(IDENTIFIER) ?: return@run

            Log.d(javaClass.simpleName, "onStartCommand: requestType=$requestType, identifier=$identifier")

            val context = this@AlarmClockService

            Notificator.notify(context, identifier)

            when (requestType) {
                REQUEST_TYPE_SET_NEXT_ALARM_CLOCK -> {
                    setNextAlarmClock(context, identifier+1)
                }
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}

private fun Long.addMinute(minute: Int): Long {
    return Calendar.getInstance().also {
        it.timeInMillis = this

        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)

        it.add(Calendar.MINUTE, 1)
    }.timeInMillis
}

private fun Long.toHumanReadableString(): String {
    return SimpleDateFormat("yyyy/MM/dd HH:mm.ss.SSS", Locale.getDefault()).format(Date(this))
}
