package com.delog.server.aggregate.statistics.presentation.dto.response

import java.math.BigInteger

data class StatisticsResponse(
    val statsId: Long,
    var username: String,
    val totalOrderCount: Int,
    val totalSpent: BigInteger,
    val highestSpent: BigInteger,
    val lowestSpent: BigInteger,
    val maxOrderGap: Int?,
    val minOrderGap: Int,
    val averageOrderGap: Int,
    val totalItemCount: Int,
    val deliveryOrderIdList: List<Long> = listOf()
) {
}
