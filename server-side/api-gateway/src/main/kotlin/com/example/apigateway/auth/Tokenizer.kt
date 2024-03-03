package com.example.apigateway.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class Tokenizer {

    @Value("\${app.token.secret}")
    private val secret: String? = null

    @Value("\${app.token.issuer}")
    private val issuer: String? = null

    suspend fun verify(token: String?): DecodedJWT? {
        try {
            val verifier: JWTVerifier = JWT.require(algorithm()).withIssuer(issuer).build()
            return Mono.just(verifier.verify(token)).awaitSingle()
        } catch (e: Exception) {
            return null
        }
    }

    private fun algorithm(): Algorithm {
        return Algorithm.HMAC256(secret)
    }

}