package com.delog.server.aggregate.statistics.persistence.jpa.mapper

import com.delog.server.aggregate.statistics.domain.Stats
import com.delog.server.aggregate.statistics.domain.entity.StatisticsEntity
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsContentResponse
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsMonthlyResponse
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsPaginateResponse
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsResponse
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class StatsPersistenceMapper {
    fun mapToEntity(stats: Stats): StatisticsEntity =
        StatisticsEntity(
            stats.statsId,
            stats.username,
            stats.totalOrderCount,
            stats.totalSpent,
            stats.highestSpent,
            stats.lowestSpent,
            stats.maxOrderGap ?: 0,
            stats.minOrderGap ?: 0,
            stats.averageOrderGap ?: 0,
            stats.totalItemCount,
            stats.summaryStartDate,
            stats.summaryEndDate,
            stats.deliveryOrderIdList,
        )

    fun mapToResponse(entity: StatisticsEntity): StatisticsResponse =
        StatisticsResponse(
            statsId = entity.id,
            username = entity.username,
            totalOrderCount = entity.totalOrderCount,
            totalSpent = entity.totalSpent,
            highestSpent = entity.highestSpent,
            lowestSpent = entity.lowestSpent,
            maxOrderGap = entity.maxOrderGap,
            minOrderGap = entity.minOrderGap,
            averageOrderGap = entity.averageOrderGap,
            totalItemCount = entity.totalItemCount,
            summaryStartDate = entity.summaryStartDate.toString(),
            summaryEndDate = entity.summaryEndDate.toString(),
            deliveryOrderIdList = entity.deliveryOrderIdList,
        )

    fun mapToPaginateResponse(findList: Page<StatisticsEntity>): StatisticsPaginateResponse {
        val contents: List<StatisticsContentResponse> =
            findList.content.map { entity ->
                StatisticsContentResponse(
                    statsId = entity.id,
                    username = entity.username,
                    totalOrderCount = entity.totalOrderCount,
                    totalSpent = entity.totalSpent,
                    averageOrderGap = entity.averageOrderGap,
                    summaryStartDate = entity.summaryStartDate.toString(),
                    summaryEndDate = entity.summaryEndDate.toString()
                )
            }

        return StatisticsPaginateResponse(
            content = contents,
            totalElements = findList.totalElements,
            totalPages = findList.totalPages,
            size = findList.size,
            number = findList.number,
            numberOfElements = findList.numberOfElements,
        )
    }

    fun mapToMonthlyResponse(weekList: List<StatisticsEntity>): StatisticsMonthlyResponse {
        val builder = MonthlyBuilder()
        weekList.forEach { builder.add(it) }
        return builder.build()
    }

    companion object {
        private class MonthlyBuilder {
            private var deliveryCount = 0
            private var orderMenuCount = 0
            private var totalSpent = BigInteger.ZERO
            private var summaryMonth = 0;

            fun add(entity: StatisticsEntity) =
                apply {
                    deliveryCount += entity.totalOrderCount
                    orderMenuCount += entity.totalItemCount
                    totalSpent += entity.totalSpent
                    summaryMonth = entity.summaryStartDate.monthValue
                }

            fun build(): StatisticsMonthlyResponse =
                StatisticsMonthlyResponse(
                    deliveryCount = deliveryCount,
                    orderMenuCount = orderMenuCount,
                    totalSpent = totalSpent,
                    summaryMonth = summaryMonth
                )
        }
    }
}
