package com.arinc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
@ConfigurationPropertiesScan
public class BazarRunner {
    public static void main(String[] args) {
        SpringApplication.run(BazarRunner.class, args);
    }

}
