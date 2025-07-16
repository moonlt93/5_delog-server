package com.delog.server.aggregate.statistics.infra.batch.config

import org.slf4j.LoggerFactory
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import kotlin.jvm.java

@Component
class StatsJobLauncher(
    private val jobLauncher: JobLauncher,
    private val statsJob: Job,
    private val jobRepository: JobRepository,
) {
    private val logger = LoggerFactory.getLogger(JobLauncher::class.java)

    @Scheduled(cron = "0 */6 * * * *", zone = "Asia/Seoul")
    fun runWeeklyStatsJob() {
        val params =
            JobParametersBuilder()
                .addLong("runAt", System.currentTimeMillis())
                .toJobParameters()

        try {
            logger.info("Starting statsJob with parameters: {}", params)

            val execution = jobLauncher.run(statsJob, params)
        } catch (e: Exception) {
            logger.error("statsJob failed", e)
        }
    }
}
