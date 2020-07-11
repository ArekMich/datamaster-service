package com.agh.dataminingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

/**
 * Main Spring Boot class responsible for launching and initializing application.
 *
 * @author Arkadiusz Michalik
 */
@SpringBootApplication
@EntityScan(basePackageClasses = {
        DataMiningServiceApplication.class,
        Jsr310JpaConverters.class
})
public class DataMiningServiceApplication {

    /**
     * Setting the default time zone on UTC
     */
    @PostConstruct
    void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DataMiningServiceApplication.class, args);
    }
}
