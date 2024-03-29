package com.example.usersservice.user

import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.user.dto.UserDto
import jakarta.validation.Valid
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class UserController(private val userService: UserService,
                     private val env: Environment? = null) {

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
    suspend fun create(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<UserDto> {
        val response = userService.register(registerRequest)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }
}