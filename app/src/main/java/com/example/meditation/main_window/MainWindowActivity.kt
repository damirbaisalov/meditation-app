package com.example.meditation.main_window

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.meditation.R
import com.google.android.material.bottomnavigation.BottomNavigationView

const val MY_APP_USER_ACTIVITY = "MY_APP_USER_ACTIVITY"
const val USER_LANGUAGE = "USER_LANGUAGE"
const val USER_NAME_SURNAME = "USER_NAME_SURNAME"
const val USER_MEDITATION_NUM = "USER_MEDITATION_NUM"
const val USER_MEDITATION_MINUTES = "USER_MEDITATION_MINUTES"
const val USER_MEDITATION_DAYS = "USER_MEDITATION_DAYS"
const val USER_DARK_MODE = "USER_DARK_MODE"
class MainWindowActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)

        if (getUserDarkModeSharedPreferences()) {
            bottomNavigationView.setBackgroundResource(R.color.bottom_nav_view_dark)
            changeStatusBarMode(android.R.color.background_dark)
        }
        else {
            bottomNavigationView.setBackgroundResource(R.color.bottom_nav_color)
            changeStatusBarMode(R.color.start_gradient_color)
        }

    }

    private fun changeStatusBarMode(id: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = ContextCompat.getColor(this, id)
    }

    private fun getUserDarkModeSharedPreferences(): Boolean {
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(
            MY_APP_USER_ACTIVITY,
            Context.MODE_PRIVATE
        )

        return sharedPreferences.getBoolean(USER_DARK_MODE, false)
    }
}