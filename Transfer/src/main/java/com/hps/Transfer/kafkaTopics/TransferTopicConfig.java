package com.hps.Transfer.kafkaTopics;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TransferTopicConfig {
    @Bean
    public NewTopic transactionTopic() {
        return TopicBuilder
                .name("transaction-stream")
             .build();}

    @Bean
        public NewTopic merchantTopic () {
            return TopicBuilder.name("merchant-topic")
                    .build();
        }

    @Bean
    public NewTopic merchantUpdateTopic () {
        return TopicBuilder.name("update-merchant")
                .build();
    }
    @Bean
    public NewTopic merchantDeleteTopic () {
        return TopicBuilder.name("delete-merchant")
                .build();
    }
    }