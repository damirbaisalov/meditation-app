package com.example.meditation.domain.usecase

import com.example.meditation.domain.models.UserName

class GetUserNameUseCase {

    fun execute(): UserName {
        return UserName(firstName = "Robert", lastName = "Miller")
    }
}