package com.delog.server.aggregate.statistics.persistence.jpa.repository

import com.delog.server.aggregate.statistics.domain.entity.StatisticsEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface StaticsRepository : JpaRepository<StatisticsEntity, Long> {


    fun findByUsernameAndCreatedAtBetween(
        username: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        pageable: Pageable
    ): Page<StatisticsEntity>

    @Query(
        """
                    SELECT s
                      FROM StatisticsEntity s
                     WHERE s.username   = :username
                       AND s.createdAt BETWEEN :startDateTime AND :endDateTime
                """
    )
    fun findByMonthlyInfoAndUserName(
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        username: String
    ): List<StatisticsEntity>


}
