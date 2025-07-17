package com.delog.server.aggregate.order.presentation.controller

import com.delog.server.aggregate.order.presentation.dto.CreateDeliveryOrderRequest
import com.delog.server.aggregate.order.presentation.dto.GetDeliveryOrderResponse
import com.delog.server.aggregate.order.presentation.dto.UpdateDeliveryOrderRequest
import com.delog.server.aggregate.order.service.DeliveryOrderService
import jakarta.validation.Valid
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate


@RestController
@RequestMapping("/api/orders")
class DeliveryOrderController(
    private val deliveryOrderService: DeliveryOrderService,
) {

    @PostMapping
    fun createOrder(@Valid @RequestBody request: CreateDeliveryOrderRequest): ResponseEntity<GetDeliveryOrderResponse> {
        val response = deliveryOrderService.createOrder(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(response)
    }

    @GetMapping("/{id}")
    fun getOrderById(@PathVariable id:Long): ResponseEntity<GetDeliveryOrderResponse> {
        val response = deliveryOrderService.findOrderById(id)
        return ResponseEntity.ok(response)
    }

    @GetMapping
    fun getAllOrders(): ResponseEntity<List<GetDeliveryOrderResponse>> {
        val response = deliveryOrderService.findAllOrders()
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(@PathVariable id:Long): ResponseEntity<Void> {
        deliveryOrderService.deleteOrder(id)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{id}")
    fun updateOrder(
        @PathVariable id:Long,
        @Valid @RequestBody request: UpdateDeliveryOrderRequest
    ): ResponseEntity<GetDeliveryOrderResponse> {
        val response = deliveryOrderService.updateOrder(id, request)
        return ResponseEntity.ok(response)
    }

    @GetMapping(params = ["date"])
    fun getOrdersByDate(
        @RequestParam("date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        date: LocalDate
    ): ResponseEntity<List<GetDeliveryOrderResponse>> {
        val response = deliveryOrderService.findOrdersByDate(date)
        return ResponseEntity.ok(response)
    }
}
