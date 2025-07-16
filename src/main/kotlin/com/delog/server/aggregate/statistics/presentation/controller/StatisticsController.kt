package com.delog.server.aggregate.statistics.presentation.controller

import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsMonthlyResponse
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsPaginateResponse
import com.delog.server.aggregate.statistics.presentation.dto.response.StatisticsResponse
import com.delog.server.aggregate.statistics.presentation.mapper.StatisticsWebMapper
import com.delog.server.aggregate.statistics.service.GetMonthlyStaticsService
import com.delog.server.aggregate.statistics.service.GetPageableStaticsListService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/stats")
class StatisticsController(
    private val getPageableStaticsListService: GetPageableStaticsListService,
    private val getMonthlyStaticsService: GetMonthlyStaticsService,
    private val statisticsWebMapper: StatisticsWebMapper,
) {
    @GetMapping("/list")
    fun getPageableStatistics(
        @RequestParam page: Int,
        @RequestParam size: Int,
        @RequestParam startDate: String,
        @RequestParam endDate: String,
    ): ResponseEntity<StatisticsPaginateResponse> {
        val getStatisticsRequest = statisticsWebMapper.mapToRequest(page, size, startDate, endDate)
        val weeklyList = getPageableStaticsListService.getWeeklyData(getStatisticsRequest)

        return ResponseEntity.ok(weeklyList)
    }

    @GetMapping("/detail/{statsId}")
    fun getPageableStatisticsDetail(
        @PathVariable statsId: Long,
    ): ResponseEntity<StatisticsResponse> {
        val weeklyList = getPageableStaticsListService.getDetailInfoById(statsId)

        return ResponseEntity.ok(weeklyList)
    }

    @GetMapping("/monthly")
    fun getMonthlyStatisticsInfo(
        @RequestParam month: Int,
    ): ResponseEntity<StatisticsMonthlyResponse> {
        val monthlyData = getMonthlyStaticsService.getMonthlyStatistics(month)
        return ResponseEntity.ok(monthlyData)
    }
}
