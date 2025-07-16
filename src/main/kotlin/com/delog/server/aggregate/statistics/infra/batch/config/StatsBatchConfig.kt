package com.delog.server.aggregate.statistics.infra.batch.config

import com.delog.server.aggregate.statistics.domain.Stats
import com.delog.server.aggregate.statistics.service.GetDeliveryOrderListService
import com.delog.server.aggregate.statistics.service.SaveStaticsDataService
import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDate

@Configuration(proxyBeanMethods = true)
@EnableBatchProcessing
class StatsBatchConfig(
    private val jobRepository: JobRepository,
    private val txManager: PlatformTransactionManager,
    private val getDeliveryOrderListService: GetDeliveryOrderListService,
    private val saveStaticsDataService: SaveStaticsDataService,
) {
    @Bean
    fun userListReader(): ItemReader<List<String>> {
        val currentDate = LocalDate.now()
        val allUsernames =
            getDeliveryOrderListService
                .getAllUsernames(currentDate)
                .distinct()
        return ListItemReader(listOf(allUsernames))
    }

    @Bean
    fun statsListProcessor(): ItemProcessor<List<String>, List<Stats>> =
        ItemProcessor { usernames ->
            val results = mutableListOf<Stats>()
            val currentDate = LocalDate.now()

            usernames.forEach { username ->

                val orders = getDeliveryOrderListService.getOrderListByUser(username, currentDate)

                if (orders.isEmpty()) return@forEach

                val stats =
                    Stats
                        .createDomainFromEntity(orders, currentDate)
                        .apply { this.username = username }

                results += stats
            }
            results
        }

    @Bean
    fun statsListWriter(): ItemWriter<List<Stats>> =
        ItemWriter { chunks ->
            val statsList: List<Stats> = chunks.first()

            statsList.forEach { stats ->
                saveStaticsDataService.saveStatisticsWeeklyData(stats)
            }
        }

    @Bean
    fun statsStep(): Step =
        StepBuilder("statsStep", jobRepository)
            .chunk<List<String>, List<Stats>>(1, txManager)
            .reader(userListReader())
            .processor(statsListProcessor())
            .writer(statsListWriter())
            .build()

    @Bean
    fun statsJob(): Job =
        JobBuilder("statsJob", jobRepository)
            .start(statsStep())
            .build()
}
