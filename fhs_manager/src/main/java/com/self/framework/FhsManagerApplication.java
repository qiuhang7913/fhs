package com.self.framework;

import com.self.framework.scheduled.dynamicScheduled.DynamicScheduledTaskService;
import com.self.framework.scheduled.dynamicScheduled.MyRunnable;
import com.self.framework.spring.extend.jpa.BaseDaoFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.self"})
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseDaoFactoryBean.class)
public class FhsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FhsManagerApplication.class, args);
//        ApplicationContext app = SpringApplication.run(FhsManagerApplication.class, args);
//        DynamicScheduledTaskService bean = app.getBean(DynamicScheduledTaskService.class);
//        bean.startCron(new MyRunnable() {
//            @Override
//            protected void excute() {
//                System.out.println("hello world text01");
//            }
//        }, "0/5 * * * * ? ");
//        bean.startCron(new MyRunnable() {
//            @Override
//            protected void excute() {
//                System.out.println("hello world text02");
//            }
//        },"0/2 * * * * ? ");
    }
}
