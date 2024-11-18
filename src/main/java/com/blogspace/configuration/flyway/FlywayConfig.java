package com.blogspace.configuration.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean
    public Flyway flyway() {
        return Flyway.configure()
                .dataSource("jdbc:postgresql://localhost:5432/BlogSpaceDB", "hostuser", "test123")
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .load();
    }
}
