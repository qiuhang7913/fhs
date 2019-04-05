package com.self.framework;

import com.alibaba.fastjson.JSON;
import com.self.framework.spring.extend.jpa.BaseDaoFactoryBean;
import com.self.framework.test.TestWxMessageHandler;
import com.self.framework.wx.WxTools;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.impl.WxMpUserServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
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
//        WxTools bean = app.getBean(WxTools.class);
//        try {
//            WxMpUserList wxMpUserList = wxMpUserService.userList("");
//            System.out.println(JSON.toJSONString(wxMpUserList));
//            WxMpMessageRouter router = new WxMpMessageRouter(bean.getWxMpService());
//            router.rule().msgType("text").handler(new TestWxMessageHandler()).end();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

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
