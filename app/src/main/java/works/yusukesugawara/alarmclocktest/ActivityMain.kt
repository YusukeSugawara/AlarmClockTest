package works.yusukesugawara.alarmclocktest

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ActivityMain : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(javaClass.simpleName, "onCreate: savedInstanceState=$savedInstanceState")

        if (savedInstanceState == null) {
            AlarmClockService.setNextAlarmClock(this@ActivityMain, 1)
        }
    }
}
