package com.example.usersservice.security

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class Tokenizer {

    @Value("\${app.token.secret}")
    private val secret: String? = null

    @Value("\${app.token.issuer}")
    private val issuer: String? = null

    fun verify(token: String?): Mono<DecodedJWT> {
        try {
            val verifier: JWTVerifier = JWT.require(algorithm()).withIssuer(issuer).build()
            return Mono.just(verifier.verify(token))
        } catch (e: Exception) {
            return Mono.empty()
        }
    }

    private fun algorithm(): Algorithm {
        return Algorithm.HMAC256(secret)
    }

}