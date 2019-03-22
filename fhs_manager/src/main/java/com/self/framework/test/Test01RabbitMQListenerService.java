package com.self.framework.test;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.self.framework.message.rabbitmq.RabbitMQListenerService;
import com.self.framework.message.rabbitmq.config.Config;
import com.self.framework.utils.DateTool;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @des topic转发模式 测试 01监听方
 * @author qiuhang
 * @version v1.0
 */
@Service
@RabbitListener(queues = Config.QUEUE_TEST_01_CODE)
public class Test01RabbitMQListenerService extends RabbitMQListenerService<String> {

    @Override
    public void execute(String content) {
        System.out.println(content);
    }
}
