package com.example.usersservice.user

import com.example.usersservice.user.dto.UserDto
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository) {
    suspend fun findById(id: Long): UserDto? =
            userRepository.findById(id)

    suspend fun create(userDto: UserDto): UserDto? =
            userRepository.create(userDto)

    suspend fun findByEmail(email: String): UserDto? =
        userRepository.findByEmail(email)

    suspend fun register(userDto: UserDto): UserDto? =
        userRepository.register(userDto)
}