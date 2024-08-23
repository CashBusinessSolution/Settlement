package com.hps.fee.kafkaProducer;

import com.hps.DTOS.FeeDTO;
import com.hps.DTOS.TransactionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeeProducer {
    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public void sendFeeMessage(FeeDTO FeeDTO) {
        log.debug("TransactionDTO before sending: {}",FeeDTO);
        Message<FeeDTO> message = MessageBuilder
                .withPayload(FeeDTO)
                .setHeader(KafkaHeaders.TOPIC, "fee-stream")
                .build();
        log.info("Sending message to 'fee-stream': {}", message);
        kafkaTemplate.send(message);
    }}