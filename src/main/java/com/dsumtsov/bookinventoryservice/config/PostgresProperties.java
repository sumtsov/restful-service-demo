package com.dsumtsov.bookinventoryservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.db")
@Getter
@Setter
public class PostgresProperties {
    private String url;
    private String name;
    private String username;
    private String password;
    private String driverClassName;
    private String containerDatasource;
    private String postgresqlVersion;
}
