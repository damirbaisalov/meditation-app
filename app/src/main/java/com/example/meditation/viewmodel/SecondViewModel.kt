package com.example.meditation.viewmodel

import android.app.Application
import android.content.*
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.meditation.main_window.MY_APP_USER_ACTIVITY
import com.example.meditation.main_window.USER_MEDITATION_DAYS
import com.example.meditation.main_window.USER_MEDITATION_MINUTES
import com.example.meditation.main_window.USER_MEDITATION_NUM
import com.example.meditation.services.TimeBriefService
import com.example.meditation.services.TimerService
import kotlin.math.roundToInt

class SecondViewModel(application: Application): AndroidViewModel(application) {

    private val resultTimeLive = MutableLiveData<String>()
    private val resultTimeBriefLive = MutableLiveData<String>()

    private val resultStartButtonStateLive = MutableLiveData<Boolean>()
    private val resultStopButtonStateLive = MutableLiveData<Boolean>()
    private val resultResumeButtonStateLive = MutableLiveData<Boolean>()
    private val resultResetButtonStateLive = MutableLiveData<Boolean>()

    private val resultGreetingStateLive = MutableLiveData<Boolean>()

    private var serviceIntent: Intent
    private var serviceBriefIntent: Intent
    private var timeStarted = false
    private var time = 300.0
    private var timeBrief = 3.0

    init {

        val updateTime: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                time = intent.getDoubleExtra(TimerService.TIME_EXTRA, 300.0)
                resultTimeLive.value = getTimeStringFromDouble(time)

                if (time.toInt()%60==0) {
                    var minutes = getUserMeditationMinutesSharedPreferences().toInt()
                    minutes+=1
                    saveUserMeditationMinutesSharedPreferences(minutes.toString())

                    if (getUserMeditationMinutesSharedPreferences().toInt()%5==0) {
                        var num = getUserMeditationNumSharedPreferences().toInt()
                        num+=1
                        saveUserMeditationNumSharedPreferences(num.toString())
                    }

                    if (getUserMeditationMinutesSharedPreferences().toInt()%1440==0){
                        var days = getUserMeditationDaysSharedPreferences().toInt()
                        days+=1
                        saveUserMeditationDaysSharedPreferences(days.toString())
                    }
                }

                if (time==0.0) {
                    stopTimer()
                    resetResultResetButtonStateLive()
                    resultGreetingStateLive.value = true
                    time = 300.0
                    timeBrief = 3.0
                }
            }
        }

        val updateBriefTime: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                    timeBrief = intent.getDoubleExtra(TimeBriefService.TIME_BRIEF_EXTRA, 3.0)

                    resultTimeBriefLive.value = getTimeStringFromDouble(timeBrief)
            }
        }

        serviceIntent = Intent(application, TimerService::class.java)
        application.registerReceiver(updateTime, IntentFilter(TimerService.TIMER_UPDATED))


        serviceBriefIntent = Intent(application, TimeBriefService::class.java)
        application.registerReceiver(updateBriefTime, IntentFilter(TimeBriefService.TIMER_BRIEF_UPDATED))

    }

    override fun onCleared() {
        Log.e("AAA", "VM cleared")
        timeStarted = false
        resetTimer()
        super.onCleared()
    }

    //TIME-LIVE
    fun getResultTimeLive(): LiveData<String> {
        return resultTimeLive
    }

    //BRIEF-TIME
    fun getResultTimeBriefLive(): LiveData<String> {
        return resultTimeBriefLive
    }

    //GREETING
    fun getResultGreetingStateLive(): LiveData<Boolean> {
        return resultGreetingStateLive
    }

    //USER-BUTTON-STATE
    fun getResultStartButtonStateLive(): LiveData<Boolean> {
        return resultStartButtonStateLive
    }

    fun getResultStopButtonStateLive(): LiveData<Boolean> {
        return resultStopButtonStateLive
    }

    fun getResultResumeButtonStateLive(): LiveData<Boolean> {
        return resultResumeButtonStateLive
    }

    fun getResultResetButtonStateLive(): LiveData<Boolean> {
        return resultResetButtonStateLive
    }

    fun resetResultStartButtonStateLive() {
        resultStartButtonStateLive.value = false
        resultStopButtonStateLive.value = true
        resultResumeButtonStateLive.value = false
        resultResetButtonStateLive.value = false
        resultGreetingStateLive.value = false
    }

    fun resetResultStopButtonStateLive() {
        resultStartButtonStateLive.value = false
        resultStopButtonStateLive.value = false
        resultResumeButtonStateLive.value = true
        resultResetButtonStateLive.value = true
    }

    fun resetResultResumeButtonStateLive() {
        resultStartButtonStateLive.value = false
        resultStopButtonStateLive.value = true
        resultResumeButtonStateLive.value = false
        resultResetButtonStateLive.value = false
    }

    fun resetResultResetButtonStateLive() {
        resultStartButtonStateLive.value = true
        resultStopButtonStateLive.value = false
        resultResumeButtonStateLive.value = false
        resultResetButtonStateLive.value = false
    }

    fun resetTimer() {
        stopTimer()
        time = 300.0
        timeBrief = 3.0
        resultTimeLive.value = getTimeStringFromDouble(time)
        resultTimeBriefLive.value = getTimeStringFromDouble(timeBrief)
    }

    fun startStopTimer() {
        if(timeStarted)
            stopTimer()
        else
            startTimer()
    }

    private fun startTimer() {
        serviceIntent.putExtra(TimerService.TIME_EXTRA, time)
        serviceBriefIntent.putExtra(TimeBriefService.TIME_BRIEF_EXTRA, timeBrief)
        getApplication<Application>().startService(serviceIntent)
        getApplication<Application>().startService(serviceBriefIntent)
        timeStarted = true
    }

    private fun stopTimer() {
        getApplication<Application>().stopService(serviceIntent)
        getApplication<Application>().stopService(serviceBriefIntent)
        timeStarted = false
    }

    private fun getTimeStringFromDouble(time: Double): String {
        val resultInt = time.roundToInt()

        val minutes = resultInt % 86400 % 3600 / 60
        val seconds = resultInt % 86400 % 3600 % 60

        return makeTimeString(minutes, seconds)
    }

    private fun makeTimeString(min: Int, sec: Int): String = String.format("%02d:%02d", min, sec)

    private fun saveUserMeditationMinutesSharedPreferences(input: String?) {
        val sharedPref = getApplication<Application>().getSharedPreferences(MY_APP_USER_ACTIVITY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_MEDITATION_MINUTES, input)
        editor.apply()
    }

    private fun getUserMeditationMinutesSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = getApplication<Application>().getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_MEDITATION_MINUTES, "0") ?:"0"
    }

    private fun saveUserMeditationDaysSharedPreferences(input: String?) {
        val sharedPref = getApplication<Application>().getSharedPreferences(MY_APP_USER_ACTIVITY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_MEDITATION_DAYS, input)
        editor.apply()
    }

    private fun getUserMeditationDaysSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = getApplication<Application>().getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_MEDITATION_DAYS, "0") ?:"0"
    }

    private fun saveUserMeditationNumSharedPreferences(input: String?) {
        val sharedPref = getApplication<Application>().getSharedPreferences(MY_APP_USER_ACTIVITY, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(USER_MEDITATION_NUM, input)
        editor.apply()
    }

    private fun getUserMeditationNumSharedPreferences(): String {
        val sharedPreferences: SharedPreferences = getApplication<Application>().getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getString(USER_MEDITATION_NUM, "0") ?:"0"
    }
}