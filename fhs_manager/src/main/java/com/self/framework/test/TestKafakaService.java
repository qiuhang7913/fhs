package com.self.framework.test;

import com.self.framework.message.kafka.KafkaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TestKafakaService extends KafkaService<String> {

    @Override
    @KafkaListener(topics = "topicQH")
    protected void onMessage(String messag) {
        System.out.println(messag);
    }

    @Override
    protected String obtainTopicName() {
        return "topicQH";
    }
}
