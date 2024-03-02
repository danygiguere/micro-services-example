package com.example.usersservice.auth

import com.example.usersservice.user.UserEntity
import com.example.usersservice.user.toAuthDto
import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class AuthMapper: BiFunction<Row, Any, AuthDto> {
    override fun apply(row: Row, o: Any): AuthDto {
        return UserEntity(
                row.get("id") as Long,
                row.get("username") as String,
                row.get("email") as String,
                row.get("password") as String
        ).toAuthDto()
    }
}