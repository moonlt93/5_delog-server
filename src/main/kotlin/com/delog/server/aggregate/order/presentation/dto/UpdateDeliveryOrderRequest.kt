package com.delog.server.aggregate.order.presentation.dto

import com.delog.server.aggregate.order.domain.DeliveryPlatform
import com.delog.server.aggregate.order.domain.FoodType
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero
import java.math.BigDecimal
import java.time.LocalDateTime

data class UpdateDeliveryOrderRequest(
    @field:NotBlank(message = "메뉴명은 필수입니다.")
    val menuName: String,
    @field:NotNull(message = "가격은 필수입니다.")
    @field:PositiveOrZero(message = "가격은 0 이상이어야 합니다.")
    val price: BigDecimal,
    @field:Min(value = 1, message = "수량은 1 이상이어야 합니다.")
    val quantity: Int,
    @field:Min(value = 1, message = "인원 수는 1 이상이어야 합니다.")
    val peopleCount: Int,
    @field:NotNull(message = "주문 시간은 필수입니다.")
    val orderDateTime: LocalDateTime,
    val category: FoodType?,
    val imageUrl: String?,
    val platform: DeliveryPlatform?,
    val memo: String?,
    @field:Min(value = 1, message = "별점은 1 이상이어야 합니다.")
    @field:Max(value = 5, message = "별점은 5 이하여야 합니다.")
    val rating: Int?,
)
