package com.example.meditation.domain.repository

import com.example.meditation.domain.models.SaveUserNameParam
import com.example.meditation.domain.models.UserName

interface UserRepository {

    fun saveName(saveUserNameParam: SaveUserNameParam): Boolean

    fun getName(): UserName
}