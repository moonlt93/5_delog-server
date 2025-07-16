package com.delog.server.aggregate.statistics.service

import com.delog.server.aggregate.statistics.persistence.jpa.mapper.StatsPersistenceMapper
import com.delog.server.aggregate.statistics.persistence.jpa.repository.StaticsRepository
import com.delog.server.aggregate.statistics.presentation.dto.StatisticsCommand
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsPaginateResponse
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsResponse
import com.delog.server.aggregate.user.application.interceptor.UserContextHolder
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetPageableStaticsListService(

    private val statsPersistenceMapper: StatsPersistenceMapper,
    private val staticsRepository: StaticsRepository

) {

    fun getWeeklyData(staticsCommand: StatisticsCommand): StatisticsPaginateResponse {

        val startDateTime = staticsCommand.startDate.atStartOfDay()
        val endDateTime = staticsCommand.endDate.atTime(23, 59, 59)                           // 23:59:59

        val pageable = PageRequest.of(
            staticsCommand.page,
            staticsCommand.size,
            Sort.by(Sort.Direction.DESC, "createdAt")
        )

        val username = UserContextHolder.getCurrentUser().toString();

        val findList =
            staticsRepository.findByUsernameAndCreatedAtBetween(username, startDateTime, endDateTime, pageable)

        return statsPersistenceMapper.mapToPaginateResponse(findList);

    }

    fun getDetailInfoById(id: Long): StatisticsResponse {

        val getDetailEntity = staticsRepository.findById(id)
            .orElseThrow { EntityNotFoundException("통계 데이터가 없습니다. id=$id") }

        return statsPersistenceMapper.mapToResponse(getDetailEntity);

    }

}
