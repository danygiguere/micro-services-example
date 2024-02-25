package com.example.usersservice.user
import com.example.usersservice.user.dto.UserDto
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class UserEntity(
    @Id var id: Long? = null,
    val username: String,
    val email: String,
    val password: String? = null
)

fun UserEntity.toDto(): UserDto = UserDto(
    id = id,
    username = username,
    email = email
)