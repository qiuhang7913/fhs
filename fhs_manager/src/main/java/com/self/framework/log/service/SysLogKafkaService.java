package com.self.framework.log.service;

import com.alibaba.fastjson.JSON;
import com.self.framework.log.bean.SysLogRecord;
import com.self.framework.message.kafka.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SysLogKafkaService extends KafkaService<SysLogRecord> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysLogRecordService sysLogRecordService;

    @Override
    @KafkaListener(topics = "topicQH")
    protected void onMessage(SysLogRecord messag) {
        System.out.println(JSON.toJSONString(messag));
        try {
            sysLogRecordService.addOrUpdata(messag);
        }catch (Exception e){
            logger.error("数据入库异常!" , e);
        }

    }

    @Override
    protected String obtainTopicName() {
        return "topicQH";
    }
}
