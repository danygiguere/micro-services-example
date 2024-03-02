package com.example.usersservice.user.dto

import com.example.usersservice.user.UserEntity

data class UserDto(
        val id: Long?,
        val username: String,
        val email: String,
)

fun UserDto.toEntity(): UserEntity = UserEntity(
        username = username,
        email = email
)