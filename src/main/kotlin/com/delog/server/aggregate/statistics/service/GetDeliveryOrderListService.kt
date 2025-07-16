package com.delog.server.aggregate.statistics.service

import com.delog.server.aggregate.order.domain.entity.DeliveryOrderEntity
import com.delog.server.aggregate.order.persistence.jpa.repository.DeliveryOrderRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional(readOnly = true)
class GetDeliveryOrderListService(
    private val deliveryOrderRepository: DeliveryOrderRepository
) {

    fun getOrderListByUser(username: String, currentDate: LocalDate): List<DeliveryOrderEntity> {

        val endDate = currentDate.minusDays(1).atTime(23, 59, 59)
        val startDate = currentDate.minusDays(6).atStartOfDay()

        return deliveryOrderRepository.findDataByWeekAtSunday(startDate, endDate, username);
    }


    fun getAllUsernames(currentDate: LocalDate): List<String> {

        val endDate = currentDate.minusDays(1).atTime(23, 59, 59)
        val startDate = currentDate.minusDays(6).atStartOfDay()

        return deliveryOrderRepository
            .findRangeUsername(startDate, endDate)
            .map { it }
    }

}
