package com.hps.Transfer.kafkaTopics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TransferTopicConfig {
    @Bean
    public NewTopic TransferTopic(){
        return TopicBuilder
                .name("transfer-stream")
                .build();
    }
}
