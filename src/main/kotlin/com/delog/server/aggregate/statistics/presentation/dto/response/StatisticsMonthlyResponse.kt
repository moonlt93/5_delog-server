package com.delog.server.aggregate.statistics.presentation.dto.response

import java.math.BigInteger

data class StatisticsMonthlyResponse(
    val deliveryCount: Int,
    val orderMenuCount: Int,
    val totalSpent: BigInteger,
)
