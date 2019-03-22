package com.self.framework.message.rabbitmq;

import com.self.framework.utils.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * @des rabbitMQ 基类
 * @author qiuhang
 * @version v1.0
 */
public abstract class RabbitMQSendService<T> implements RabbitTemplate.ReturnCallback {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * MQ send方法
     * @param exchangeName
     * @param queueName
     * @param context
     */
    public void send(String exchangeName, String queueName, T context) {
        try {
            logger.info("发送时间为:" + DateTool.getDataStrByLocalDateTime(LocalDateTime.now(),DateTool.FORMAT_L6));
            logger.info("发送内容为: " + context);
            this.rabbitTemplate.setReturnCallback(this);
            this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
                if (!ack) {
                    logger.error("消息发送失败" + cause + correlationData.toString());
                } else {
                    logger.info("消息发送成功 ");
                }
            });
            this.rabbitTemplate.convertAndSend(exchangeName, queueName, context);

        }catch (Exception e){
            logger.error("MQ数据发送异常!",e);
        }

    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        logger.info("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }
}
