package com.epam.esm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
        System.setProperty("javax.net.ssl.trustStore", "C:/Users/Ivan_Zhyuliuk/keystore.p12");
        System.setProperty("javax.net.ssl.trustStorePassword", "Qwe123okA123");
    }
}
