package com.delog.server.common.exception.dto

import org.springframework.http.HttpStatus
import java.time.LocalDateTime

data class ErrorResponse(
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val status: Int,
    val error: String,
    val message: String,
    val details: Map<String, String>? = null,
) {
    constructor(httpStatus: HttpStatus, message: String, details: Map<String, String>? = null) : this(
        status = httpStatus.value(),
        error = httpStatus.reasonPhrase,
        message = message,
        details = details,
    )
}
