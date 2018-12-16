package com.self.framework.ucenter;

import com.self.framework.spring.jpa.extend.BaseDaoFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.self"})
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
public class FhsUcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(FhsUcenterApplication.class, args);
    }
}
