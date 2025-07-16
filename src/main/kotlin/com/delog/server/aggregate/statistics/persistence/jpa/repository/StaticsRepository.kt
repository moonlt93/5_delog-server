package com.delog.server.aggregate.statistics.persistence.jpa.repository

import com.delog.server.aggregate.statistics.domain.entity.StatisticsEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface StaticsRepository : JpaRepository<StatisticsEntity, Long> {


    fun findByUsernameAndCreatedAtBetween(
        username:      String,
        startDateTime: LocalDateTime,
        endDateTime:   LocalDateTime,
        pageable: Pageable
    ): Page<StatisticsEntity>
}
