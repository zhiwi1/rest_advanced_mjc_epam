package com.epam.esm.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@PropertySource("classpath:property/database.properties")
public class DatabaseConfig {
    @Autowired
    private DatabaseConfigParam databaseConfigParam;

    @Profile("prod")
    @Bean
    public DataSource dataSource() {
        var config = new HikariConfig();
        config.setDriverClassName(databaseConfigParam.getDriver());
        config.setJdbcUrl(databaseConfigParam.getUrl());
        config.setUsername(databaseConfigParam.getUsername());
        config.setPassword(databaseConfigParam.getPassword());
        config.setMaximumPoolSize(Integer.parseInt(databaseConfigParam.getPoolSize()));
        return new HikariDataSource(config);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

