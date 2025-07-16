package com.delog.server.aggregate.user.presentation.dto

import java.time.LocalDateTime

data class UserInfoResponse(
    val username: String,
    val name: String,
    val createdAt: LocalDateTime?,
)
