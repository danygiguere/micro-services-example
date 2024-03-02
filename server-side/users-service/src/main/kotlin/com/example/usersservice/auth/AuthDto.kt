package com.example.usersservice.auth

import com.example.usersservice.user.dto.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore

data class AuthDto(
    val id: Long?,
    val username: String,
    val email: String,
    @JsonIgnore()
    val password: String?
)

fun AuthDto.toUserDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email
)