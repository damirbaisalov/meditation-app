package com.example.meditation.domain.usecase

import com.example.meditation.domain.models.UserName
import com.example.meditation.domain.repository.UserRepository

class GetUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(): UserName {
        return userRepository.getName()
    }
}