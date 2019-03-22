package com.self.framework.test;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.self.framework.message.rabbitmq.RabbitMQListenerService;
import com.self.framework.message.rabbitmq.config.Config;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = Config.QUEUE_TEST_FANOUT_C_CODE)
public class FanoutTestCRabbitMQListenerService extends RabbitMQListenerService<TestBean> {

    @Override
    public void execute(TestBean content) {
        System.out.println(JSON.toJSONString(content));
    }
}
