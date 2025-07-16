package com.delog.server.aggregate.statistics.service

import com.delog.server.aggregate.statistics.domain.Stats
import com.delog.server.aggregate.statistics.persistence.jpa.mapper.StatsPersistenceMapper
import com.delog.server.aggregate.statistics.persistence.jpa.repository.StaticsRepository
import org.springframework.stereotype.Service

@Service
class SaveStaticsDataService(
    private val staticsRepository: StaticsRepository,
    private val statsPersistenceMapper: StatsPersistenceMapper,
) {
    fun saveStatisticsWeeklyData(stats: Stats) {
        val createEntity = statsPersistenceMapper.mapToEntity(stats)
        staticsRepository.save(createEntity)
    }
}
