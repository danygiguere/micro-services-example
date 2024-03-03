package com.example.apigateway.auth

import com.auth0.jwt.exceptions.JWTDecodeException
import kotlinx.coroutines.runBlocking
import mu.KLogging
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter(private val tokenizer: Tokenizer) : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {
    class Config()

    companion object: KLogging()

    override fun apply(config: Config): GatewayFilter {
         return GatewayFilter { exchange, chain ->
            val jwt = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)?.removePrefix("Bearer ")
            runBlocking {
                if (jwt != null && isValidJWT(jwt)) {
                    chain.filter(exchange)
                } else {
                    onError(exchange, HttpStatus.UNAUTHORIZED)
                }
            }
         }
    }

    private fun onError(exchange: ServerWebExchange, httpStatus: HttpStatus): Mono<Void> {
        exchange.response.statusCode = httpStatus
        return exchange.response.setComplete()
    }

    suspend fun isValidJWT(token: String?): Boolean {
        try {
            val decodedJwt = tokenizer.verify(token)
            return decodedJwt != null
                    && !decodedJwt.issuer.isNullOrEmpty()
                    && !decodedJwt.subject.isNullOrEmpty()
                    && !decodedJwt.claims.isNullOrEmpty()
                    && !decodedJwt.signature.isNullOrEmpty()
        } catch (e: JWTDecodeException) {
            return false
        }
    }

}