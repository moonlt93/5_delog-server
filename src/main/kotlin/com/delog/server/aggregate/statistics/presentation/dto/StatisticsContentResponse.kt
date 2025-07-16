package com.delog.server.aggregate.statistics.presentation.dto

import java.math.BigInteger

data class StatisticsContentResponse(

    val statsId: Long,
    var username: String,
    val totalOrderCount: Int,
    val totalSpent: BigInteger,
    val averageOrderGap: Int,

)
