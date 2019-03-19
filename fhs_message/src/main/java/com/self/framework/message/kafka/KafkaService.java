package com.self.framework.message.kafka;

import com.self.framework.utils.ObjectCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * @des 基础kafaka服务类
 * @author qiuhang
 * @version v1.0
 * @param <T>
 */
public abstract class KafkaService<T> {

    @Autowired
    private KafkaTemplate<String,T> kafkaTemplate;

    /**
     * 发送消息到kafaka
     * @return
     */
    public Boolean sendMsg(T v){
        if (ObjectCheckUtil.checkIsNullOrEmpty(v)){
            throw new RuntimeException("空数据!");
        }
        kafkaTemplate.send(obtainTopicName(),v);
        return true;
    }

    /**
     * 消费数据
     */
    protected abstract void onMessage(T messag);

    protected abstract String obtainTopicName();
}
