package com.hps.fee.kafkaConsumer;

import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.fee.mappers.MerchantMapper;

import com.hps.fee.models.Merchant;
import com.hps.fee.repositories.MerchantRepository;
import com.hps.fee.services.FeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;



@Slf4j
@RequiredArgsConstructor
@Component
public class FeeConsumer {

    private final FeeService feeService;
    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;

    @KafkaListener(topics = "merchant-topic", groupId = "mygroup")
    public void handleMerchant(MerchantDTO merchantDTO) {
        log.info("Consuming the message from 'merchant-topic' topic: {}", merchantDTO);

        if (merchantDTO.getTaxRate() != null) {
            Merchant merchant = merchantMapper.toEntity(merchantDTO);
            log.info("Mapping MerchantDTO to Merchant: {}, {}",merchantDTO, merchant);

            merchantRepository.save(merchant);
            log.info("Saved merchant with ID: {}", merchantDTO.getMerchantId());
        } else {
            log.warn("The tax rate in the message is null: {}", merchantDTO);
        }
    }

    @KafkaListener(topics = "transaction-stream", groupId = "mygroup")
    public void handleTransaction(TransactionDTO transactionDTO) {
        log.info("Consuming the message from 'transaction-stream' topic: {}", transactionDTO);

        try {
            feeService.processTransaction(transactionDTO);
        } catch (Exception e) {
            log.error("Error processing transaction: {}", e.getMessage(), e);
        }
    }
}
