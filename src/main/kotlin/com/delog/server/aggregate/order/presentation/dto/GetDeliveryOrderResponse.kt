package com.delog.server.aggregate.order.presentation.dto

import com.delog.server.aggregate.order.domain.DeliveryPlatform
import com.delog.server.aggregate.order.domain.FoodType
import java.math.BigDecimal
import java.time.LocalDateTime

data class GetDeliveryOrderResponse(
    val id: Long,
    val menuName: String,
    val price: BigDecimal,
    val quantity: Int,
    val peopleCount: Int,
    val orderDateTime: LocalDateTime,
    val category: FoodType?,
    val imageUrl: String?,
    val platform: DeliveryPlatform?,
    val memo: String?,
    val rating: Int?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
    val username: String,
)
