package com.example.usersservice.auth

import com.example.usersservice.requests.RegisterRequest
import com.example.usersservice.user.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val userService: UserService
) {

    @PostMapping("/register")
    suspend fun create(@Valid @RequestBody registerRequest: RegisterRequest): ResponseEntity<Any> {
        val user = userService.findByEmail(registerRequest.email)
        return if (user != null) {
            ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists.")
        } else {
            ResponseEntity.ok(userService.register(registerRequest))
        }
    }

//    @PostMapping("login")
    //Todo: login
}