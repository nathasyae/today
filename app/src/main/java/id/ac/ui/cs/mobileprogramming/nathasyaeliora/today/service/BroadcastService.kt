package id.ac.ui.cs.mobileprogramming.nathasyaeliora.Today.service

import android.app.Service
import android.content.Intent
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log


class BroadcastService : Service() {
    private val TAG = "BroadcastService"
    var intent = Intent(COUNTDOWN_BR)
    lateinit var sharedPreferences: SharedPreferences
    lateinit var countDownTimer: CountDownTimer

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "Starting Today Service...")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent !=null && intent.getExtras()!=null){
            val value = intent.getStringExtra("START_BTN");
        }

        if (intent?.getStringExtra("START_BTN").equals("start")!!) {
            Log.i(TAG, "Starting timer...")
            sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)

            var millis = sharedPreferences.getLong("time", 30000)
            if (millis / 1000 == 0L) {
                millis = 30000
            }
            countDownTimer = object : CountDownTimer(millis, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    Log.i(TAG, "Countdown seconds remaining:" + millisUntilFinished / 1000)
                    intent?.putExtra("countdown", millisUntilFinished)
                    sendBroadcast(intent)
                }

                override fun onFinish() {}
            }
            countDownTimer.start()
        }

        else if(intent?.getStringExtra("START_BTN").equals("reset")!!){
            countDownTimer!!.cancel()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    fun startTimer(){
        countDownTimer.start()
    }
    
    override fun onDestroy() {
        countDownTimer!!.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val COUNTDOWN_BR = "id.ac.ui.cs.mobileprogramming.nathasyaeliora.today"
    }
}
