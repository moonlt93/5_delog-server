package com.delog.server.aggregate.order.domain.entity

import com.delog.server.aggregate.order.domain.DeliveryPlatform
import com.delog.server.aggregate.order.domain.FoodType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "delivery_order")
@EntityListeners(AuditingEntityListener::class)
class DeliveryOrderEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    @Column(nullable = false)
    var menuName: String,
    @Column(nullable = false)
    var price: BigDecimal,
    @Column(nullable = false)
    var quantity: Int,
    @Column(nullable = false)
    var peopleCount: Int,
    @Column(nullable = false)
    var orderDateTime: LocalDateTime,
    @Enumerated(EnumType.STRING)
    var category: FoodType?,
    var imageUrl: String?,
    @Enumerated(EnumType.STRING)
    var platform: DeliveryPlatform?,
    @Lob
    var memo: String?,
    var rating: Int?,
    @Column(nullable = false)
    var username: String,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,
    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime? = null,
)
