package com.example.meditation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.meditation.main_window.MY_APP_USER_ACTIVITY
import com.example.meditation.main_window.MainWindowActivity
import com.example.meditation.main_window.USER_LANGUAGE
import java.util.*

private const val SPLASH_SCREEN_DELAY: Long = 500
class MainActivity : AppCompatActivity() {

    private lateinit var locale: Locale

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreenTheme)

        setContentView(R.layout.activity_main)

        Handler().postDelayed({

            if (getLanguageSharedPreferences()==0)
                setLocaleWithoutRefresh("en-us")
            else
                setLocaleWithoutRefresh("kk")

            startActivity(Intent(this, MainWindowActivity::class.java))
            finish()
        }, SPLASH_SCREEN_DELAY)
    }

    private fun setLocaleWithoutRefresh(languageName: String) {
        locale = Locale(languageName)
        val res = resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = locale
        res.updateConfiguration(conf, dm)
    }

    private fun getLanguageSharedPreferences(): Int {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getInt(USER_LANGUAGE, 0)
    }
}