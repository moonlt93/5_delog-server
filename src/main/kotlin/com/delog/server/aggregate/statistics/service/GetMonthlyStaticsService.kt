package com.delog.server.aggregate.statistics.service

import com.delog.server.aggregate.statistics.persistence.jpa.mapper.StatsPersistenceMapper
import com.delog.server.aggregate.statistics.persistence.jpa.repository.StaticsRepository
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsMonthlyResponse
import com.delog.server.aggregate.user.application.interceptor.UserContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.YearMonth

@Service
@Transactional(readOnly = true)
class GetMonthlyStaticsService(
    private val staticsRepository: StaticsRepository,
    private val statsPersistenceMapper: StatsPersistenceMapper
) {

    fun getMonthlyStatistics(month: Int): StatisticsMonthlyResponse {

        val username = UserContextHolder.getCurrentUser().toString();
        val yearMonth = getYearMonth(month)

        val startDateTime = yearMonth.atDay(1).atStartOfDay()
        val endDateTime = yearMonth.atEndOfMonth().atTime(23, 59, 59, 999)

        val weekList = staticsRepository.findByMonthlyInfoAndUserName(startDateTime, endDateTime, username)

        return statsPersistenceMapper.mapToMonthlyResponse(weekList)
    }


    private fun getYearMonth(month: Int): YearMonth {
        val currentYear = LocalDate.now().year
        val yearMonth = YearMonth.of(currentYear, month)
        return yearMonth
    }

}
