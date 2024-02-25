package com.example.usersservice.user

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
}