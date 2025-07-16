package com.delog.server.aggregate.user.service

import com.delog.server.aggregate.user.persistence.jpa.mapper.UserPersistenceMapper
import com.delog.server.aggregate.user.persistence.jpa.repository.UserRepository
import com.delog.server.aggregate.user.presentation.dto.UserInfoResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class GetUserInfoService(
    private val userRepository: UserRepository,
    private val userPersistenceMapper: UserPersistenceMapper
) {

    fun findByUsername(username: String): UserInfoResponse {

        val userInfo = userRepository.findById(username).orElseThrow {

            EntityNotFoundException("User with username $username not found")

        }

        return userPersistenceMapper.mapToInfoResponse(userInfo);
    }

}
