package com.example.meditation.data.repository

import android.content.Context
import com.example.meditation.domain.models.SaveUserNameParam
import com.example.meditation.domain.models.UserName
import com.example.meditation.domain.repository.UserRepository

const val SHARED_PREFS_NAME = "shared_prefs_name"
const val KEY_FIRST_NAME = "first_name"
const val KEY_LAST_NAME = "last_name"
const val DEFAULT_NAME = "default last name"

class UserRepositoryImpl(context: Context): UserRepository {

    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun saveName(saveUserNameParam: SaveUserNameParam): Boolean {
        sharedPreferences.edit().putString(KEY_FIRST_NAME, saveUserNameParam.name).apply()
        return true
    }

    override fun getName(): UserName {

        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, DEFAULT_NAME) ?: DEFAULT_NAME

        return UserName(firstName = firstName, lastName = lastName)
    }
}