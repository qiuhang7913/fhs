package com.self.framework.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.self"})
public class FhsUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FhsUcenterApplication.class, args);
    }
}
