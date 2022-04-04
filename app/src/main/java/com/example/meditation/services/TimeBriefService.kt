package com.example.meditation.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.*

class TimeBriefService: Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    private val timer = Timer()

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val time = intent.getDoubleExtra(TIME_BRIEF_EXTRA, 3.0)
        timer.scheduleAtFixedRate(TimeTask(time), 0, 1000)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Double): TimerTask() {
        override fun run() {

            if (time<=0.0) {
                time = 4.0
            }

            val intent = Intent(TIMER_BRIEF_UPDATED)
            time--

            intent.putExtra(TIME_BRIEF_EXTRA, time)
            sendBroadcast(intent)
        }
    }

    companion object
    {
        const val TIMER_BRIEF_UPDATED = "TIMER_BRIEF_UPDATED"
        const val TIME_BRIEF_EXTRA = "TIME_BRIEF_EXTRA"
    }
}