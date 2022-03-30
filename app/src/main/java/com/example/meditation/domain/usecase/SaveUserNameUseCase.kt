package com.example.meditation.domain.usecase

import com.example.meditation.domain.models.SaveUserNameParam
import com.example.meditation.domain.repository.UserRepository

class SaveUserNameUseCase(private val userRepository: UserRepository) {

    fun execute(param: SaveUserNameParam): Boolean {
        val result: Boolean = userRepository.saveName(saveUserNameParam = param)
        return result
    }
}