package com.ppg.proj.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    @Resource
    private DataSource dataSource;

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        return new Flyway(Flyway.configure()
        .dataSource(dataSource)
        .baselineOnMigrate(true));
    }
}
