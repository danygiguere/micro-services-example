package com.example.usersservice.user.dto

import com.example.usersservice.user.UserEntity
import com.fasterxml.jackson.annotation.JsonIgnore

data class UserDto(
        val id: Long?,
        val username: String,
        val email: String,
        @JsonIgnore()
        val password: String?
)

fun UserDto.toUserEntity(): UserEntity = UserEntity(
        username = username,
        email = email
)