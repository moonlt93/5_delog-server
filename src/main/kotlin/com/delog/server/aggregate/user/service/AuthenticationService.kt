package com.delog.server.aggregate.user.service

import com.delog.server.aggregate.user.application.interceptor.UserContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val getUserService: GetUserInfoService,
) {
    fun authenticate(username: String): Boolean {
        val userInfo = getUserService.findByUsername(username)

        UserContextHolder.setUser(userInfo.username)

        return true
    }
}
