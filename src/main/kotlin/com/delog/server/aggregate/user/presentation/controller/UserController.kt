package com.delog.server.aggregate.user.presentation.controller

import com.delog.server.aggregate.user.presentation.dto.CreateUserInfoRequest
import com.delog.server.aggregate.user.presentation.dto.CreateUserResponse
import com.delog.server.aggregate.user.presentation.dto.UserInfoResponse
import com.delog.server.aggregate.user.service.GetUserInfoService
import com.delog.server.aggregate.user.service.RegisterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(
    private val registerUserService: RegisterUserService,
    private val getUserInfoService: GetUserInfoService,
) {
    @PostMapping("/register")
    fun registerUser(
        @RequestBody request: CreateUserInfoRequest,
    ): ResponseEntity<CreateUserResponse> {
        val userInfo = registerUserService.registerUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(userInfo)
    }

    @GetMapping("/{username}")
    fun getUserInfo(
        @PathVariable username: String,
    ): ResponseEntity<UserInfoResponse> {
        val getUserInfo = getUserInfoService.findByUsername(username)
        return ResponseEntity.ok(getUserInfo)
    }
}
