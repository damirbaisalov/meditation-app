package com.example.meditation.data.storage

import com.example.meditation.data.storage.models.User

interface UserStorage {

    fun save(user: User): Boolean

    fun get(): User
}