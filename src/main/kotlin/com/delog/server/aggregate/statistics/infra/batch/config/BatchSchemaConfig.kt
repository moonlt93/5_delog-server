package com.delog.server.aggregate.statistics.infra.batch.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.DataSourceInitializer
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import javax.sql.DataSource

@Configuration
class BatchSchemaConfig {
    @Bean
    fun dataSourceInitializer(dataSource: DataSource): DataSourceInitializer {
        val initializer = DataSourceInitializer()
        initializer.setDataSource(dataSource)

        val populator = ResourceDatabasePopulator()
        populator.addScript(ClassPathResource("schema-batch.sql"))
        initializer.setDatabasePopulator(populator)

        return initializer
    }
}
