package com.example.meditation.data.storage.sharedprefs

import android.content.Context
import com.example.meditation.data.storage.UserStorage
import com.example.meditation.data.storage.models.User

const val SHARED_PREFS_NAME = "shared_prefs_name"
const val KEY_FIRST_NAME = "first_name"
const val KEY_LAST_NAME = "last_name"
const val DEFAULT_FIRST_NAME = "default first name"
const val DEFAULT_LAST_NAME = "default last name"

class SharedPrefUserStorage(context: Context): UserStorage {

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun save(user: User): Boolean {
        sharedPreferences.edit().putString(KEY_FIRST_NAME, user.firstName).apply()
        sharedPreferences.edit().putString(KEY_LAST_NAME, user.lastName).apply()
        return true
    }

    override fun get(): User {
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, DEFAULT_FIRST_NAME) ?: DEFAULT_FIRST_NAME
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, DEFAULT_LAST_NAME) ?: DEFAULT_LAST_NAME

        return User(firstName = firstName, lastName = lastName)
    }
}