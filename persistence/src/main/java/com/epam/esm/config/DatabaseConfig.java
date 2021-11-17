package com.epam.esm.config;


import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.epam.esm")
@EntityScan("com.epam.esm")
@EnableJpaRepositories(basePackages = "com.epam.esm.dao.datajpa")
public class DatabaseConfig {


}


