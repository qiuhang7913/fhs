package com.self.framework.message.rabbitmq;

import com.rabbitmq.client.Channel;
import com.self.framework.utils.DateTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;

import java.time.LocalDateTime;

/**
 * @des rabbitMQ 监听基类
 * @author qiuhang
 * @version v1.0
 */
public abstract class RabbitMQListenerService<T>{
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RabbitHandler
    public void process(T content, Channel channel, Message message){
        logger.info("接收时间为["+ this.getClass().toString() +"]:" + DateTool.getDataStrByLocalDateTime(LocalDateTime.now(),DateTool.FORMAT_L6));
        try {
            execute(content);
        } catch (Exception e) {
            logger.error("数据处理异常!", e);
        }
        //告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发,丢弃这条消息
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e) {
            logger.error("MQ消费数据异常!", e);
        }
    }

    /**
     * 核心执行方法
     * @param content
     */
    public abstract void execute(T content);
}
