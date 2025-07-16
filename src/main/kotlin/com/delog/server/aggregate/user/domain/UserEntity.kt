package com.delog.server.aggregate.user.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener::class)
class UserEntity(

    @Id
    @Column(name = "username", nullable = false)
    var username: String,

    var name: String,

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
)
