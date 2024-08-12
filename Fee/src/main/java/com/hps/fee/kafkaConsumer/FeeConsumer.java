package com.hps.fee.kafkaConsumer;

import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.fee.mappers.MerchantMapper;
import com.hps.fee.models.Fee;
import com.hps.fee.models.Merchant;
import com.hps.fee.repositories.MerchantRepository;
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
    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;

    @KafkaListener(topics = "merchant-topic", groupId = "mygroup")
    public void handleMerchant(MerchantDTO merchantDTO) {
        log.info("Consuming the message from 'merchant-topic' topic: {}", merchantDTO);

        if (merchantDTO.getTaxRate() != null) {
            Merchant merchant = merchantMapper.toEntity(merchantDTO);

            merchantRepository.save(merchant);
            log.info("Saved merchant with ID: {}", merchantDTO.getMerchantId());
        } else {
            log.warn("The tax rate in the message is null: {}", merchantDTO);
        }
    }

    @KafkaListener(topics = "transaction-stream", groupId = "mygroup")
    public void handleTransaction(TransactionDTO transactionDTO) {
        log.info("Consuming the message from 'transaction-stream' topic: {}", transactionDTO);

        if (transactionDTO.getAmount() != null && transactionDTO.getMerchantId() != null) {
            Merchant merchant = merchantRepository.findById(transactionDTO.getMerchantId()).orElse(null);

            if (merchant != null) {
                BigDecimal taxRate = merchant.getTaxRate();
                BigDecimal amount = transactionDTO.getAmount();
                Fee fee = feeService.calculateFee(amount, taxRate);

                log.info("Calculated fee for amount {} is {}", amount, fee.getFeeAmount());

                // Créer un FeeDTO et l'envoyer à Kafka
                //FeeDTO feeDTO = new FeeDTO(transactionDTO.getId(), fee.getFeeAmount());
                //feeProducer.sendFee(feeDTO);
            } else {
                log.error("Merchant with ID {} not found", transactionDTO.getMerchantId());
            }
        } else {
            log.warn("The amount or merchant ID in the message is null: {}", transactionDTO);
        }
    }
}
