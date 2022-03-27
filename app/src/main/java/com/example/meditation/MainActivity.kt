package com.example.meditation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.meditation.main_window.MainWindowActivity

private const val SPLASH_SCREEN_DELAY: Long = 1500
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.SplashScreenTheme)

        setContentView(R.layout.activity_main)

        Handler().postDelayed({
            startActivity(Intent(this, MainWindowActivity::class.java))
            finish()
        }, SPLASH_SCREEN_DELAY)
    }
}