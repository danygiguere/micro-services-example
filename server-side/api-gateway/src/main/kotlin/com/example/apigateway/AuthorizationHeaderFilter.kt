package com.example.apigateway

import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class AuthorizationHeaderFilter : AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>(Config::class.java) {
    class Config()

    override fun apply(config: Config): GatewayFilter {
         return GatewayFilter { exchange, chain ->
            val jwt = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)?.removePrefix("Bearer ")
            if (jwt != null && isJwtValid(jwt)) {
                chain.filter(exchange)
            } else {
                onError(exchange, HttpStatus.UNAUTHORIZED)
            }
         }
    }

    private fun onError(exchange: ServerWebExchange, httpStatus: HttpStatus): Mono<Void> {
        exchange.response.statusCode = httpStatus
        return exchange.response.setComplete()
    }

    fun isJwtValid(token: String?): Boolean {
        return try {
            val decodedJWT = JWT.decode(token)
            !decodedJWT.issuer.isNullOrEmpty() && !decodedJWT.subject.isNullOrEmpty()
        } catch (e: JWTDecodeException) {
            false
        }
    }

}