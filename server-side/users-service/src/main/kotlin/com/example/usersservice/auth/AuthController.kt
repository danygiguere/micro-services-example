package com.example.usersservice.auth

import com.example.usersservice.requests.LoginRequest
import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.security.Tokenizer
import com.example.usersservice.user.dto.UserDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
    private val tokenizer: Tokenizer,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    suspend fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<Any> {
        val user = authService.findByEmail(request.email)
        return if (user != null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.")
        } else {
            ResponseEntity.ok(authService.register(request))
        }
    }

    @PostMapping("/login")
    suspend fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<AuthDto> {
        val auth = authService.findByEmail(request.email)
        val passwordMatch = passwordEncoder.matches(request.password, auth?.password)
        return if (auth != null && passwordMatch) {
            ResponseEntity.ok().header("Authorization", tokenizer.createBearerToken(auth.id.toString())).body(auth)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }
}