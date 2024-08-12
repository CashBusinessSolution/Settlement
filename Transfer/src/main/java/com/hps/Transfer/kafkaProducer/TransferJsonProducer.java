package com.hps.Transfer.kafkaProducer;

import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
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

    private final KafkaTemplate<String, TransactionDTO> kafkaTemplate;

    public void sendTransactionMessage(TransactionDTO transactionDTO) {
        log.debug("TransactionDTO before sending: {}", transactionDTO);
        Message<TransactionDTO> message = MessageBuilder
                .withPayload(transactionDTO)
                .setHeader(KafkaHeaders.TOPIC, "transaction-stream")
                .build();
        log.info("Sending message to 'transaction-stream': {}", message);
        kafkaTemplate.send(message);
    }

    public void sendMerchantMessage(MerchantDTO merchantDTO) {
        Message<MerchantDTO> message = MessageBuilder
                .withPayload(merchantDTO)
                .setHeader(KafkaHeaders.TOPIC, "merchant-topic")
                .build();
        log.info("Sending message to 'merchant-topic': {}", message);
        kafkaTemplate.send(message);
    }
}
