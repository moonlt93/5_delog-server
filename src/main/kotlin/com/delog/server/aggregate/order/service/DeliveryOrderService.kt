package com.delog.server.aggregate.order.service

import com.delog.server.aggregate.order.domain.entity.DeliveryOrderEntity
import com.delog.server.aggregate.order.persistence.jpa.repository.DeliveryOrderRepository
import com.delog.server.aggregate.order.presentation.dto.CreateDeliveryOrderRequest
import com.delog.server.aggregate.order.presentation.dto.UpdateDeliveryOrderRequest
import com.delog.server.aggregate.order.presentation.mapper.DeliveryOrderMapper
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class DeliveryOrderService(
    private val deliveryOrderRepository: DeliveryOrderRepository,
    private val deliveryOrderMapper: DeliveryOrderMapper,
) {
    @Transactional
    fun createOrder(request: CreateDeliveryOrderRequest): DeliveryOrderEntity {
        val entity = deliveryOrderMapper.toEntity(request)
        return deliveryOrderRepository.save(entity)
    }

    fun findOrderById(id: Long): DeliveryOrderEntity =
        deliveryOrderRepository
            .findById(id)
            .orElseThrow { EntityNotFoundException("해당 ID의 주문을 찾을 수 없습니다: $id") }

    fun findAllOrders(): List<DeliveryOrderEntity> = deliveryOrderRepository.findAll()

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
    ): DeliveryOrderEntity {
        val existingEntity = findOrderById(id)

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
        return existingEntity
    }
}
