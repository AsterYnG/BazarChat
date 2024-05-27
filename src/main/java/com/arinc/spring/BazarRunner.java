package com.arinc.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = {"com.arinc.*"})
@EntityScan(basePackages = {"com.arinc.*"})
@EnableJpaRepositories(basePackages = "com.arinc.*")
public class BazarRunner {
    public static void main(String[] args) {
         var context = SpringApplication.run(BazarRunner.class,args);
        System.out.println("hi");
    }

}
