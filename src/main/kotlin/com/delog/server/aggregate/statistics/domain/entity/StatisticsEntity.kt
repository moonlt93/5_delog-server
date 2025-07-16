package com.delog.server.aggregate.statistics.domain.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.math.BigInteger
import java.time.LocalDateTime


@Entity
@Table(name = "stats")
@EntityListeners(AuditingEntityListener::class)
class StatisticsEntity(

    @Id
    @Column(name = "statsId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(nullable = false)
    var username: String,

    @Column(nullable = false)
    var totalOrderCount: Int,

    @Column(nullable = false)
    var totalSpent: BigInteger,

    @Column(nullable = false)
    var highestSpent: BigInteger,

    @Column(nullable = false)
    var lowestSpent: BigInteger,

    @Column(nullable = false)
    var maxOrderGap: Int,

    @Column(nullable = false)
    var minOrderGap: Int,

    @Column(nullable = false)
    var averageOrderGap: Int,

    @Column(nullable = false)
    var totalItemCount: Int,

    @ElementCollection
    @CollectionTable(
        name = "delivery_orderId_list",
        joinColumns = [JoinColumn(name = "statsId")],
        uniqueConstraints = [UniqueConstraint(columnNames = ["statsId", "deliveryOrderIdList"])]
    )
    val deliveryOrderIdList: List<Long> = listOf(),

    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: LocalDateTime? = null,

)
