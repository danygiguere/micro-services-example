package com.example.usersservice.auth

import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.user.dto.UserDto
import org.springframework.stereotype.Service

@Service
class AuthService(val authRepository: AuthRepository) {
    suspend fun findByEmail(email: String): AuthDto? =
        authRepository.findByEmail(email)

    suspend fun register(request: RegisterRequest): UserDto? =
        authRepository.register(request)
}