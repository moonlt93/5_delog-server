package com.delog.server.aggregate.user.persistence.jpa.mapper

import com.delog.server.aggregate.user.domain.UserEntity
import com.delog.server.aggregate.user.presentation.dto.CreateUserInfoRequest
import com.delog.server.aggregate.user.presentation.dto.CreateUserResponse
import com.delog.server.aggregate.user.presentation.dto.UserInfoResponse
import org.springframework.stereotype.Component

@Component
class UserPersistenceMapper {

    fun mapToEntity(request: CreateUserInfoRequest): UserEntity {
        return UserEntity(
            request.username,
            request.name
        )
    }

    fun mapToResponse(savedEntity: UserEntity): CreateUserResponse {
        return CreateUserResponse(
            savedEntity.username,
            savedEntity.name
        )
    }

    fun mapToInfoResponse(entity: UserEntity): UserInfoResponse {
        return UserInfoResponse(
            entity.username,
            entity.name,
            entity.createdAt
        )
    }

}
