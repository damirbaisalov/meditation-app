package com.example.meditation.main_window

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
class MainWindowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_window)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }
}