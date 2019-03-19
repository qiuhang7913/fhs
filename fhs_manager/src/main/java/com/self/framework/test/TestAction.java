package com.self.framework.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestAction {

    @Autowired
    TestKafkaBeanService testBeanKafkaService;

    @RequestMapping(value = "t", method = RequestMethod.GET)
    @ResponseBody
    public String testKafaka(){
        TestBean testBean = new TestBean();
        testBean.setValue1("aa");
        testBean.setValue2("bbb");
        testBeanKafkaService.sendMsg(testBean);
        return "";
    }
}
