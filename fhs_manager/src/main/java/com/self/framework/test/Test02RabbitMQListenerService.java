package com.self.framework.test;

import com.rabbitmq.client.Channel;
import com.self.framework.message.rabbitmq.RabbitMQListenerService;
import com.self.framework.message.rabbitmq.config.Config;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @des topic转发模式 测试 02监听方
 * @author qiuhang
 * @version v1.0
 */
@Service
@RabbitListener(queues = Config.QUEUE_TEST_02_CODE)
public class Test02RabbitMQListenerService extends RabbitMQListenerService<String> {

    @Override
    public void execute(String content) {
        System.out.println(content);
    }
}
