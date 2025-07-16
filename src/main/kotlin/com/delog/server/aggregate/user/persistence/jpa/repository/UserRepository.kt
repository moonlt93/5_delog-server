package com.delog.server.aggregate.user.persistence.jpa.repository

import com.delog.server.aggregate.user.domain.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, String>
