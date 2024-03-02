package com.example.usersservice.auth

import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.requests.toEntity
import com.example.usersservice.user.dto.UserDto
import com.example.usersservice.user.toUserDto
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class AuthRepository(private val databaseClient: DatabaseClient,
                     private val authMapper: AuthMapper,
                     private val passwordEncoder: PasswordEncoder
) {

    companion object: KLogging()

    suspend fun findByEmail(email: String): AuthDto? =
        databaseClient.sql("SELECT * FROM users WHERE email = :email")
            .bind("email", email)
            .map(authMapper::apply)
            .awaitOneOrNull()

    suspend fun register(request: RegisterRequest): UserDto =
        databaseClient.sql("INSERT INTO users (username, email, password) VALUES (:username, :email, :password)")
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("username", request.username)
            .bind("email", request.email)
            .bind("password", passwordEncoder.encode(request.password))
            .fetch()
            .first()
            .map { row ->
                val id = row["id"] as Long
                val userEntity = request.toEntity().copy(id = id)
                userEntity.toUserDto()
            }
            .awaitSingle()
}