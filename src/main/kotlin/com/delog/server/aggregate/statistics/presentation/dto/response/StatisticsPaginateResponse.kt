package com.delog.server.aggregate.statistics.presentation.dto.response

data class StatisticsPaginateResponse(
    val content: List<StatisticsContentResponse>,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int,
    val numberOfElements: Int,
)
