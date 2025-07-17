package com.delog.server.common.exception

import com.delog.server.common.exception.dto.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException::class)
    protected fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        // 어떤 필드가 어떤 이유로 실패했는지 상세 정보 추출
        val fieldErrors =
            e.bindingResult.fieldErrors.associate {
                it.field to (it.defaultMessage ?: "유효하지 않은 값입니다.")
            }

        val errorResponse =
            ErrorResponse(
                httpStatus = HttpStatus.BAD_REQUEST,
                message = "입력값이 유효하지 않습니다.",
                details = fieldErrors,
            )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFoundException(e: EntityNotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse(
                httpStatus = HttpStatus.NOT_FOUND,
                message = e.message ?: "요청한 리소스를 찾을 수 없습니다.",
            )
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    protected fun handleIllegalArgumentException(e: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse(
                httpStatus = HttpStatus.BAD_REQUEST,
                message = e.message ?: "잘못된 요청입니다.",
            )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    protected fun handleGlobalException(e: Exception): ResponseEntity<ErrorResponse> {
        val errorResponse =
            ErrorResponse(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
                message = "서버 내부 오류가 발생했습니다.",
            )
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse)
    }
}
