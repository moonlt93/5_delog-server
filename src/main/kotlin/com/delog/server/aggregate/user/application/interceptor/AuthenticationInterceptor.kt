package com.delog.server.aggregate.user.application.interceptor

import com.delog.server.aggregate.user.service.AuthenticationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.lang.Exception

@Component
class AuthenticationInterceptor(
    private val authenticationService: AuthenticationService,
) : HandlerInterceptor {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val username = request.getHeader("Authorization")

        logger.info("Pre-handling request for authentication username {}", request.getHeader("Authorization"))

        if (username.isNullOrBlank()) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json; charset=UTF-8"
            response.writer.write(" username is not nullable")
            return false
        }

        if (!authenticationService.authenticate(username)) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json; charset=UTF-8"
            response.writer.write("Invalid username $username")
            return false
        }

        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        UserContextHolder.clearUser()
        logger.info("@@@ Successful Request completed for authentication @@@@@")
    }
}
