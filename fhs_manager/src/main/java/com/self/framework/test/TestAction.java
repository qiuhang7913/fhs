package com.self.framework.test;

import com.self.framework.message.rabbitmq.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestAction {

    @Autowired
    TestKafakaService testKafakaService;

    @Autowired
    TestRabbitMQSendService testRabbitMQSendService;

    @Autowired
    FanoutTestRabbitMQSendService sendService;

    @RequestMapping(value = "t", method = RequestMethod.GET)
    @ResponseBody
    public String testKafaka(){
        testKafakaService.sendMsg("hello world!");
        return "";
    }

    @RequestMapping(value = "t_mq_topic", method = RequestMethod.GET)
    @ResponseBody
    public String testRabbitMQ(){
        testRabbitMQSendService.send(Config.EXCHANGE_TEST_01_CODE, Config.QUEUE_TEST_02_CODE, "hello world!");
        return "";
    }

    @RequestMapping(value = "t_mq_fanout", method = RequestMethod.GET)
    @ResponseBody
    public String testRabbitMQFanout(){
        TestBean testBean = new TestBean();
        testBean.setValue("111");
        testBean.setValue2("222");
        sendService.send(Config.EXCHANGE_TEST_02_CODE,"", testBean);
        return "";
    }
}
