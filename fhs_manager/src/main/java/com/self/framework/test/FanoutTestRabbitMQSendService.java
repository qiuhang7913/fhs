package com.self.framework.test;

import com.rabbitmq.client.Channel;
import com.self.framework.message.rabbitmq.RabbitMQListenerService;
import com.self.framework.message.rabbitmq.RabbitMQSendService;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @des fanout广播模式 测试 发送方
 * @author qiuhang
 * @version v1.0
 */
@Service
public class FanoutTestRabbitMQSendService extends RabbitMQSendService<TestBean> {

}

class TestBean implements Serializable {
    private String value;
    private String value2;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}
