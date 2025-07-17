package com.delog.server.aggregate.order.service

import com.delog.server.aggregate.order.persistence.jpa.repository.DeliveryOrderRepository
import com.delog.server.aggregate.order.presentation.dto.CreateDeliveryOrderRequest
import com.delog.server.aggregate.order.presentation.dto.GetDeliveryOrderResponse
import com.delog.server.aggregate.order.presentation.dto.UpdateDeliveryOrderRequest
import com.delog.server.aggregate.order.presentation.mapper.DeliveryOrderMapper
import com.delog.server.aggregate.user.application.interceptor.UserContextHolder
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class DeliveryOrderService(
    private val deliveryOrderRepository: DeliveryOrderRepository,
    private val deliveryOrderMapper: DeliveryOrderMapper,
) {
    @Transactional
    fun createOrder(request: CreateDeliveryOrderRequest): GetDeliveryOrderResponse {
        val username = UserContextHolder.getCurrentUser().toString();
        val entity = deliveryOrderMapper.toEntity(request, username)
        val savedEntity = deliveryOrderRepository.save(entity)
        return deliveryOrderMapper.toResponse(savedEntity)
    }

    fun findOrderById(id: Long): GetDeliveryOrderResponse {
        val entity =
            deliveryOrderRepository
                .findById(id)
                .orElseThrow { EntityNotFoundException("해당 ID의 주문을 찾을 수 없습니다: $id") }
        return deliveryOrderMapper.toResponse(entity)
    }

    fun findAllOrders(): List<GetDeliveryOrderResponse> =
        deliveryOrderRepository
            .findAll()
            .map { deliveryOrderMapper.toResponse(it) }

    @Transactional
    fun deleteOrder(id: Long) {
        if (!deliveryOrderRepository.existsById(id)) {
            throw EntityNotFoundException("해당 ID의 주문을 찾을 수 없습니다: $id")
        }
        deliveryOrderRepository.deleteById(id)
    }

    @Transactional
    fun updateOrder(
        id: Long,
        request: UpdateDeliveryOrderRequest,
    ): GetDeliveryOrderResponse {
        val existingEntity =
            deliveryOrderRepository
                .findById(id)
                .orElseThrow { EntityNotFoundException("해당 ID의 주문을 찾을 수 없습니다: $id") }

        existingEntity.apply {
            this.menuName = request.menuName
            this.price = request.price
            this.quantity = request.quantity
            this.peopleCount = request.peopleCount
            this.orderDateTime = request.orderDateTime
            this.category = request.category
            this.imageUrl = request.imageUrl
            this.platform = request.platform
            this.memo = request.memo
            this.rating = request.rating
        }
        return deliveryOrderMapper.toResponse(existingEntity)
    }

    fun findOrdersByDate(date: LocalDate): List<GetDeliveryOrderResponse> {
        // 해당 날짜의 시작 시간
        val startDateTime = date.atStartOfDay()
        // 해당 날짜의 끝 시간
        val endDateTime = date.plusDays(1).atStartOfDay()

        return deliveryOrderRepository
            .findAllByOrderDateTimeBetween(startDateTime, endDateTime)
            .map { deliveryOrderMapper.toResponse(it) }
    }
}
