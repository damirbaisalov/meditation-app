package com.example.meditation.data.repository

import com.example.meditation.data.storage.models.User
import com.example.meditation.data.storage.sharedprefs.SharedPrefUserStorage
import com.example.meditation.domain.models.SaveUserNameParam
import com.example.meditation.domain.models.UserName
import com.example.meditation.domain.repository.UserRepository


class UserRepositoryImpl(private val sharedPrefUserStorage: SharedPrefUserStorage): UserRepository {


    override fun saveName(saveUserNameParam: SaveUserNameParam): Boolean {
        val user = mapToStorage(saveUserNameParam)

        val result = sharedPrefUserStorage.save(user)
        return result
    }

    override fun getName(): UserName {
        val user = sharedPrefUserStorage.get()
        return mapToDomain(user)
    }

    private fun mapToStorage(saveUserNameParam: SaveUserNameParam): User {
        return User(firstName = saveUserNameParam.name, lastName = "")
    }

    private fun mapToDomain(user: User): UserName {
        return UserName(firstName = user.firstName, lastName = user.lastName)
    }
}