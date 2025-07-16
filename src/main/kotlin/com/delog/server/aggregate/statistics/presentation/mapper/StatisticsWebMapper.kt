package com.delog.server.aggregate.statistics.presentation.mapper

import com.delog.server.aggregate.statistics.presentation.dto.StatisticsCommand
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Component
class StatisticsWebMapper {
    fun mapToRequest(
        page: Int,
        size: Int,
        startDate: String,
        endDate: String,
    ): StatisticsCommand {
        val ofPattern = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        return StatisticsCommand(
            page,
            size,
            LocalDate.parse(startDate, ofPattern),
            LocalDate.parse(endDate, ofPattern),
        )
    }
}
