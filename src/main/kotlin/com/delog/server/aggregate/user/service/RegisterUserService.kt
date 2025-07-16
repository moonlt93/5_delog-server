package com.delog.server.aggregate.user.service

import com.delog.server.aggregate.user.persistence.jpa.mapper.UserPersistenceMapper
import com.delog.server.aggregate.user.persistence.jpa.repository.UserRepository
import com.delog.server.aggregate.user.presentation.dto.CreateUserInfoRequest
import com.delog.server.aggregate.user.presentation.dto.CreateUserResponse
import org.springframework.stereotype.Service

@Service
class RegisterUserService(
    private val userRepository: UserRepository,
    private val userPersistenceMapper: UserPersistenceMapper
) {

    fun registerUser(request: CreateUserInfoRequest): CreateUserResponse {

        val userEntity = userPersistenceMapper.mapToEntity(request);
        val savedEntity = userRepository.save(userEntity);
        return userPersistenceMapper.mapToResponse(savedEntity);

    }

}
