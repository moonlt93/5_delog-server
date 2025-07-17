package com.delog.server.aggregate.order.persistence.jpa.repository

import com.delog.server.aggregate.order.domain.entity.DeliveryOrderEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface DeliveryOrderRepository: JpaRepository<DeliveryOrderEntity, Long>{

    fun findAllByOrderDateTimeBetween(start: LocalDateTime, end: LocalDateTime): List<DeliveryOrderEntity>
}
