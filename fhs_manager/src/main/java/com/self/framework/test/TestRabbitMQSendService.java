package com.self.framework.test;

import com.self.framework.message.rabbitmq.RabbitMQSendService;
import org.springframework.stereotype.Service;

/**
 * @des topic转发模式 测试 发送方
 * @author qiuhang
 * @version v1.0
 */
@Service
public class TestRabbitMQSendService extends RabbitMQSendService<String> {
}
