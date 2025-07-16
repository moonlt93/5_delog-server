package com.delog.server.aggregate.statistics.domain

import com.delog.server.aggregate.order.domain.entity.DeliveryOrderEntity
import java.math.BigInteger
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit


class Stats(
    val statsId: Long,
    var username: String,
    val totalOrderCount: Int,
    val totalSpent: BigInteger,
    val highestSpent: BigInteger,
    val lowestSpent: BigInteger,
    val maxOrderGap: Int?,
    val minOrderGap: Int?,
    val averageOrderGap: Int,
    val totalItemCount: Int,
    val deliveryOrderIdList: List<Long> = listOf()
) {

    companion object {

        fun createDomainFromEntity(deliveryOrderList: List<DeliveryOrderEntity>, currentDate: LocalDate): Stats {

            val sortedList = deliveryOrderList.sortedBy { it.price }
            val firstPrice = sortedList.first().price
            val lastPrice = sortedList.last().price

            val endDate = currentDate.minusDays(1).atTime(23, 59, 59)
            val startDate = currentDate.minusDays(7).atStartOfDay()

            val gaps = calculateWeeklyOrderGapsInDays(deliveryOrderList, startDate, endDate);

            return Stats(
                0L,
                deliveryOrderList.first().username,
                deliveryOrderList.size,
                deliveryOrderList.map { it.price }.reduce { a, b -> a.add(b) }.toBigInteger(),
                BigInteger.valueOf(lastPrice.toLong()),
                BigInteger.valueOf(firstPrice.toLong()),
                gaps.first?.toInt() ?: 0,
                gaps.second?.toInt() ?: 0,
                (gaps.first + gaps.second / 2).toInt(),
                deliveryOrderList.sumBy { it.quantity },
                deliveryOrderList.map { it.id }
            );
        }

        fun calculateWeeklyOrderGapsInDays(
            orders: List<DeliveryOrderEntity>,
            startDate: LocalDateTime,
            endDate: LocalDateTime
        ): Pair<Long, Long> {
            val start = startDate.toLocalDate()
            val end = endDate.toLocalDate()

            if (orders.isEmpty()) {
                val fullDays = ChronoUnit.DAYS.between(start, end) + 1
                return fullDays to 0L
            }

            val sorted = orders
                .map { it.orderDateTime }
                .filter { !it.isBefore(startDate) && !it.isAfter(endDate) }
                .sorted()

            val timePoints = listOf(startDate) + sorted + listOf(endDate)
            val gapsInDays = timePoints.mapIndexed { idx, prev ->
                val next = timePoints.getOrNull(idx + 1) ?: return@mapIndexed 0L
                val rawDays = ChronoUnit.DAYS.between(prev.toLocalDate(), next.toLocalDate())
                if (idx == timePoints.size - 2) rawDays
                else maxOf(rawDays - 1, 0)
            }.dropLast(1)

            val tailGap = sorted
                .last()
                .let { last ->
                    ChronoUnit.DAYS.between(last.toLocalDate(), endDate.toLocalDate())
                }

            val maxGap = gapsInDays.maxOrNull() ?: tailGap

            if (tailGap >= maxGap) {
                return maxGap to 0L
            }

            return maxGap to tailGap;
        }
    }

}
