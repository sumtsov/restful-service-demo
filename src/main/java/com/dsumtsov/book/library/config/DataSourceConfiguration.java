package com.dsumtsov.book.library.config;

import com.dsumtsov.book.library.config.properties.PostgresProperties;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import javax.sql.DataSource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfiguration {
    private final PostgresProperties postgresProperties;

    @Bean(name = "dataSource")
    @ConditionalOnProperty(name = "app.db.container-datasource", havingValue = "false")
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(postgresProperties.getDriverClassName())
                .url(postgresProperties.getUrl())
                .username(postgresProperties.getUsername())
                .password(postgresProperties.getPassword())
                .build();
    }

    @Bean(name = "dataSource")
    @ConditionalOnProperty(name = "app.db.container-datasource", havingValue = "true")
    public DataSource getContainerDatasource() {
        PostgreSQLContainer postgreSQLContainer =
                (PostgreSQLContainer) new PostgreSQLContainer(postgresProperties.getPostgresqlVersion())
                .withDatabaseName(postgresProperties.getName())
                .withUsername(postgresProperties.getUsername())
                .withPassword(postgresProperties.getPassword())
                .withExposedPorts(5432)
                .waitingFor(new LogMessageWaitStrategy()
                        .withRegEx(".*database system is ready to accept connections.*\\s")
                        .withTimes(2)
                        .withStartupTimeout(Duration.of(60, ChronoUnit.SECONDS)));

        postgreSQLContainer.start();

        return DataSourceBuilder.create()
                .driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

    @Bean
    @DependsOn("dataSource")
    public Integer migrateFlyway(DataSource dataSource) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(dataSource)
                .load();

        return flyway.migrate();
    }
}
