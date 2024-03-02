package com.example.usersservice.user

import com.example.usersservice.requests.LoginRequest
import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.requests.toUserDto
import com.example.usersservice.security.Tokenizer
import com.example.usersservice.user.dto.UserDto
import jakarta.validation.Valid
import mu.KLogging
import org.springframework.core.env.Environment
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService,
                     private val env: Environment? = null,
                     private val tokenizer: Tokenizer,
                     private val passwordEncoder: PasswordEncoder
) {

    companion object: KLogging()

    @GetMapping("/status/check")
    fun statusCheck(): String {
        return "Users api is working on port " + env?.getProperty("local.server.port")
    }

    @GetMapping("/users/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<UserDto> {
        val response = userService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/register")
    suspend fun register(@Valid @RequestBody request: RegisterRequest): ResponseEntity<UserDto> {
        if (userService.findByEmail(request.email) != null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.")
        }
        val newUser = userService.register(request.toUserDto())
        return if (newUser != null) {
            ResponseEntity.ok().header("Authorization", tokenizer.createBearerToken(newUser.id)).body(newUser)
        } else {
            logger.error("Error while registering user with email: ${request.email}")
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PostMapping("/login")
    suspend fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserDto> {
        val user = userService.findByEmail(request.email)
        val passwordMatch = passwordEncoder.matches(request.password, user?.password)
        return if (user != null && passwordMatch) {
            ResponseEntity.ok().header("Authorization", tokenizer.createBearerToken(user.id)).body(user)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }

}