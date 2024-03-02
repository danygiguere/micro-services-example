package com.example.usersservice.requests

data class LoginRequest(
        val id: Long?,
        val username: String,
        val email: String,
        val password: String
)
