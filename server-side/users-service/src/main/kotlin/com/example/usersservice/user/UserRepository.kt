package com.example.usersservice.user

import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.requests.toEntity
import com.example.usersservice.user.dto.UserDto
import com.example.usersservice.user.dto.toEntity
import kotlinx.coroutines.reactor.awaitSingle
import mu.KLogging
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.r2dbc.core.awaitOneOrNull
import org.springframework.stereotype.Repository

@Repository
class UserRepository(private val databaseClient: DatabaseClient,
                     private val userMapper: UserMapper) {

    companion object: KLogging()

    suspend fun findById(id: Long): UserDto? =
            databaseClient.sql("SELECT * FROM users WHERE id = :id")
                    .bind("id", id)
                    .map(userMapper::apply)
                    .awaitOneOrNull()

    suspend fun findByEmail(email: String): UserDto? =
        databaseClient.sql("SELECT * FROM users WHERE email = :email")
            .bind("email", email)
            .map(userMapper::apply)
            .awaitOneOrNull()

    suspend fun create(userDto: UserDto): UserDto =
            databaseClient.sql("INSERT INTO users (username, email) VALUES (:username, :email)")
                    .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
                    .bind("username", userDto.username)
                    .bind("email", userDto.email)
                    .fetch()
                    .first()
                    .map { row ->
                        val id = row["id"] as Long
                        val userEntity = userDto.toEntity().copy(id = id)
                        userEntity.toDto()
                    }
                    .awaitSingle()

    suspend fun register(registerRequest: RegisterRequest): UserDto =
        databaseClient.sql("INSERT INTO users (username, email, password) VALUES (:username, :email, :password)")
            .filter { statement, _ -> statement.returnGeneratedValues("id").execute() }
            .bind("username", registerRequest.username)
            .bind("email", registerRequest.email)
            .bind("password", registerRequest.password)
            .fetch()
            .first()
            .map { row ->
                val id = row["id"] as Long
                val userEntity = registerRequest.toEntity().copy(id = id)
                userEntity.toDto()
            }
            .awaitSingle()
}