package com.epam.esm.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;;import javax.persistence.Entity;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
@EntityScan("com.epam.esm")
public class DatabaseConfig {

}


