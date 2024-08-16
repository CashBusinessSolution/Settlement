package com.hps.fee.kafkaTopics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class FeeTopicConfig {

    @Bean
    public NewTopic feeTopic() {
        return TopicBuilder
                .name("fee-stream")
                .build();
    }

}
