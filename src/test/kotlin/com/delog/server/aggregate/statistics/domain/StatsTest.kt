package com.delog.server.aggregate.statistics.domain

import com.delog.server.aggregate.order.domain.entity.DeliveryOrderEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class StatsTest {
    private fun order(
        id: Long,
        username: String,
        price: Double,
        dateTime: LocalDateTime,
    ) = DeliveryOrderEntity(
        id = id,
        menuName = "menu",
        username = username,
        price = BigDecimal.valueOf(price),
        quantity = 1,
        peopleCount = 1,
        orderDateTime = dateTime,
        category = null,
        imageUrl = null,
        platform = null,
        memo = null,
        rating = null,
    )

    @Nested
    @DisplayName("calculateWeeklyOrderGapsInDays 테스트")
    inner class CalculateGapsTests {
        val sunday = LocalDate.of(2025, 7, 13).atTime(23, 59, 59)
        val monday = LocalDate.of(2025, 7, 7).atStartOfDay()

        @Test
        fun `주문이 한 건도 없으면 7일`() {
            val (maxGap, minGap) = Stats.calculateWeeklyOrderGapsInDays(emptyList(), monday, sunday)
            assertEquals(7L, maxGap)
            assertEquals(0L, minGap)
        }

        @Test
        fun `주간에만 두 건 주문한 경우 max, min 계산 min 이 max 보다 크거나 같으면 0`() {
            // 월요일 새벽 주문, 금요일 새벽 주문
            val orders =
                listOf(
                    order(1, "u", 10.0, monday.plusDays(0)),
                    order(2, "u", 20.0, monday.plusDays(4)),
                )
            val (maxGap, minGap) = Stats.calculateWeeklyOrderGapsInDays(orders, monday, sunday)
            // 월→금: rawDays=4 → 공백 3일 화수목
            assertEquals(3L, maxGap)
            assertEquals(2L, minGap)
        }

        @Test
        fun `월요일 하루 여러 주문이 있으면 공백 0`() {
            val t = monday.plusHours(6)
            val orders =
                listOf(
                    order(1, "u", 5.0, t),
                    order(2, "u", 7.0, t.plusMinutes(30)),
                )
            val (maxGap, minGap) = Stats.calculateWeeklyOrderGapsInDays(orders, monday, sunday)

            // 월요일 주문하고 6일이지남
            assertEquals(6L, maxGap)
            assertEquals(0L, minGap)
        }
    }

    @Nested
    @DisplayName("createDomainFromEntity 테스트")
    inner class CreateDomainTests {
        @Test
        fun `기본 통계값 생성 검증`() {
            val now = LocalDateTime.of(2025, 7, 14, 1, 0)
            val orders =
                listOf(
                    order(1, "userA", 100.0, now.minusDays(6)),
                    order(2, "userA", 200.0, now.minusDays(2)),
                    order(3, "userA", 50.0, now.minusDays(1)),
                )
            val stats = Stats.createDomainFromEntity(orders, now.toLocalDate())

            assertEquals("userA", stats.username)
            assertEquals(3, stats.totalOrderCount)
            assertEquals(200.toBigInteger(), stats.highestSpent)
            assertEquals(50.toBigInteger(), stats.lowestSpent)

            assertEquals(3, stats.maxOrderGap)
            assertEquals(0, stats.minOrderGap)
            assertIterableEquals(listOf(1L, 2L, 3L), stats.deliveryOrderIdList)
        }
    }
}
