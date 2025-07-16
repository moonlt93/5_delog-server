package com.delog.server.aggregate.statistics.presentation.dto

import java.time.LocalDate

data class StatisticsCommand(
    val page: Int,
    val size: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
