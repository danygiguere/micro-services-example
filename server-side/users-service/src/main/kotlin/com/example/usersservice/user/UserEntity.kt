package com.example.usersservice.user
import com.example.usersservice.user.dto.UserDto
import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class UserEntity(
    @Id var id: Long? = null,
    val username: String,
    val email: String,
    @JsonIgnore()
    val password: String? = null
)

fun UserEntity.toUserDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email,
    password = password
)