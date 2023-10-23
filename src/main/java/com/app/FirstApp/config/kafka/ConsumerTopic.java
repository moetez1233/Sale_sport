package com.app.FirstApp.config.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerTopic {
    private static final Logger log= LoggerFactory.getLogger(ConsumerTopic.class);

    @KafkaListener(topics = "topicForTest", groupId = "group_id") // topicForTest is the name of the topic
    public void consume(ConsumerRecord<String, String> payload){
        log.info("key: {}", payload.key());
        log.info("Headers: {}", payload.headers());
        log.info("Partion: {}", payload.partition());
        log.info("message: {}", payload.value());

    }
}
