package com.hps.fee.kafkaConsumer;


import com.hps.DTOS.FeeDTO;
import com.hps.DTOS.TransferDTO;
import com.hps.fee.kafkaProducer.FeeProducer;
import com.hps.fee.models.Fee;
import com.hps.fee.services.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Component
public class FeeConsumer {

    private final FeeService feeService;
    private final FeeProducer feeProducer;

    @KafkaListener(topics = "transfer-stream", groupId = "mygroup")
    public void consumeMsg(TransferDTO transferDTO) {
        log.info("Consuming the message from 'transfer-stream' topic: {}", transferDTO);

        if (transferDTO.getAmount() != null) {
            BigDecimal amount = transferDTO.getAmount();
            Fee fee = feeService.calculateFee(amount);

            log.info("Calculated fee for amount {} is {}", amount, fee.getFee());
            // Créer un FeeDTO et l'envoyer à Kafka
            FeeDTO feeDTO = new FeeDTO(transferDTO.getId(), fee.getFee());
            feeProducer.sendFee(feeDTO);
        } else {
            log.warn("The amount in the message is null: {}", transferDTO);
        }
    }}