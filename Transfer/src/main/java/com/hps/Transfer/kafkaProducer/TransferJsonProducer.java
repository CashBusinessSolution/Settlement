package com.hps.Transfer.kafkaProducer;

import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.DTOS.TransferDTO;
import com.hps.Transfer.models.Transfer;
import com.hps.Transfer.repositories.TransferRepository;
import com.hps.Transfer.services.TransferService;
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
    private final TransferService transferService;
    private final TransferRepository transferRepository;

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

    public void sendTransferMessage(TransferDTO transferDTO) {
        // Mettre à jour le solde du compte
        transferService.updateAccountBalance(transferDTO.getId(), transferDTO.getAmount());

        Transfer updatedTransfer = transferRepository.findById(transferDTO.getId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Créer un nouveau TransferDTO avec les valeurs mises à jour
        TransferDTO updatedTransferDTO = new TransferDTO(
                updatedTransfer.getId(),
                updatedTransfer.getFeeAmount(),
                updatedTransfer.getMerchantId(),
                updatedTransfer.getSettlementOption(),
                updatedTransfer.getFeeStructure(),
                updatedTransfer.getTaxRate(),
                updatedTransfer.getTransactionId(),
                updatedTransfer.getAmount(),
                updatedTransfer.getBankAccountNumber(),
                updatedTransfer.getAccountBalance()
        );
        // Construire et envoyer le message Kafka
        Message<TransferDTO> message = MessageBuilder
                .withPayload(updatedTransferDTO)
                .setHeader(KafkaHeaders.TOPIC, "Transfer-topic")
                .build();

        log.info("Sending message to 'Transfer-topic': {}", message);
        kafkaTemplate.send(message);
    }
    public void sendUpdateMerchantMessage(MerchantDTO merchantDTO) {
        Message<MerchantDTO> message = MessageBuilder
                .withPayload(merchantDTO)
                .setHeader(KafkaHeaders.TOPIC, "update-merchant")
                .build();
        log.info("Sending message to  'update-merchant':{}", message);
        kafkaTemplate.send(message);
    }

    public void sendDeleteMerchantMessage(MerchantDTO merchantDTO) {
        Message<MerchantDTO> message = MessageBuilder
                .withPayload(merchantDTO)
                .setHeader(KafkaHeaders.TOPIC, "delete-merchant")
                .build();
        log.info("Sending message to  'delete-merchant':{}", message);
        kafkaTemplate.send(message);
    }
}
