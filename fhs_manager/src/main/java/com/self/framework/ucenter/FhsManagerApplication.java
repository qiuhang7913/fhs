package com.self.framework.ucenter;

import com.self.framework.spring.extend.jpa.BaseDaoFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.self"})
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
public class FhsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FhsManagerApplication.class, args);
    }
}
