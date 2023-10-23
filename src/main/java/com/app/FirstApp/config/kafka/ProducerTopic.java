package com.app.FirstApp.config.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerTopic {
    private static final Logger logger= LoggerFactory.getLogger(ProducerTopic.class);
    private KafkaTemplate<String,String> kafkaTemplate;

    public ProducerTopic(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){
        logger.info("message : {}",message);
        String nameTopic="topicForTest";
        kafkaTemplate.send(nameTopic,message);
    }

}
