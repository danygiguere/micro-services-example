package com.example.usersservice.requests

import com.example.usersservice.user.UserEntity
import com.example.usersservice.user.dto.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class RegisterRequest(
    val id: Long?,

    @get:NotNull()
    @get:NotEmpty()
    @get:Size(min = 6, max = 25, message = "{username.size}")
    val username: String,

    @get:NotNull()
    @get:NotEmpty()
    @get:Email(message = "{email}")
    val email: String,

//    @JsonIgnore()
    val password: String
) {

}

fun RegisterRequest.toEntity(): UserEntity = UserEntity(
    username = username,
    email = email,
    password = password
)