package com.delog.server.aggregate.statistics.persistence.jpa.mapper

import com.delog.server.aggregate.statistics.domain.Stats
import com.delog.server.aggregate.statistics.domain.entity.StatisticsEntity
import com.delog.server.aggregate.statistics.presentation.dto.StatisticsContentResponse
import com.delog.server.aggregate.statistics.presentation.dto.StatisticsPaginateResponse
import com.delog.server.aggregate.statistics.presentation.dto.StatisticsResponse
import org.springframework.data.domain.Page
import org.springframework.stereotype.Component

@Component
class StatsPersistenceMapper {

    fun mapToEntity(stats: Stats): StatisticsEntity {
        return StatisticsEntity(
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
            stats.deliveryOrderIdList
        )
    }

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
            deliveryOrderIdList = entity.deliveryOrderIdList
        )

    fun mapToPaginateResponse(findList: Page<StatisticsEntity>): StatisticsPaginateResponse {

        val contents: List<StatisticsContentResponse> = findList.content.map { entity ->
            StatisticsContentResponse(
                statsId           = entity.id,
                username          = entity.username,
                totalOrderCount   = entity.totalOrderCount,
                totalSpent        = entity.totalSpent,
                averageOrderGap   = entity.averageOrderGap
            )
        }

        return StatisticsPaginateResponse(
            content           = contents,
            totalElements     = findList.totalElements,
            totalPages        = findList.totalPages,
            size              = findList.size,
            number            = findList.number,
            numberOfElements  = findList.numberOfElements
        )
    }

}


