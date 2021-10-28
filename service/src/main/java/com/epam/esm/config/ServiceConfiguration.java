package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
public class ServiceConfiguration {
    @Bean
    public ModelMapper modelMapper() {
        final var mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setFieldMatchingEnabled(true)
                .setSkipNullEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        return mapper;
    }

//    @Bean
//    public LocalEntityManagerFactoryBean managerFactoryBean() {
//        final var result = new LocalEntityManagerFactoryBean();
//        result.setPersistenceUnitName("com.epam.esm");
//        return result;
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        final var result = new JpaTransactionManager();
//        result.setEntityManagerFactory(managerFactoryBean().getObject());
//        return result;
//    }
}
