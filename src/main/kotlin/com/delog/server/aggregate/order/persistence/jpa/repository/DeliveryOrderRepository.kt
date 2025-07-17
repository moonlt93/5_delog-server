package com.delog.server.aggregate.order.persistence.jpa.repository

import com.delog.server.aggregate.order.domain.entity.DeliveryOrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime

interface DeliveryOrderRepository : JpaRepository<DeliveryOrderEntity, Long> {
    fun findAllByOrderDateTimeBetween(
        start: LocalDateTime,
        end: LocalDateTime,
    ): List<DeliveryOrderEntity>

    @Query(
        """
            SELECT o
              FROM DeliveryOrderEntity o
             WHERE o.username      = :username
               AND o.orderDateTime    BETWEEN :startDate AND :endDate
                """,
    )
    fun findDataByWeekAtSunday(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime,
        @Param("username") username: String,
    ): List<DeliveryOrderEntity>

    @Query(
        """
            SELECT o.username
              FROM DeliveryOrderEntity o
             WHERE o.orderDateTime    BETWEEN :startDate AND :endDate
                """,
    )
    fun findRangeUsername(
        @Param("startDate") startDate: LocalDateTime,
        @Param("endDate") endDate: LocalDateTime,
    ): List<String>
}
