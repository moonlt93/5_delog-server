package com.delog.server.aggregate.user.application.interceptor

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val authenticationInterceptor: AuthenticationInterceptor,
) : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(authenticationInterceptor)
            .addPathPatterns(
                "/api/user/{username}",
                "/api/stats/**",
                "/api/orders/**",
            )
    }
}
