package com.delog.server.aggregate.statistics.presentation.dto.response

import java.math.BigInteger

data class StatisticsContentResponse(
    val statsId: Long,
    var username: String,
    val totalOrderCount: Int,
    val totalSpent: BigInteger,
    val averageOrderGap: Int,
    val summaryStartDate: String,
    val summaryEndDate: String,
)
