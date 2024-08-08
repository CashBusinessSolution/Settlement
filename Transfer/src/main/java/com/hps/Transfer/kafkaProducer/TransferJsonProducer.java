package com.hps.Transfer.kafkaProducer;

import com.hps.DTOS.TransferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferJsonProducer {

    private final KafkaTemplate<String, TransferDTO> kafkaTemplate;

    public void sendMessage(TransferDTO transferDTO) {
        Message<TransferDTO> message = MessageBuilder
                .withPayload(transferDTO)
                .setHeader(KafkaHeaders.TOPIC, "transfer-stream")
                .build();
        log.info("Sending message: {}", message);
        kafkaTemplate.send(message);
    }
}
