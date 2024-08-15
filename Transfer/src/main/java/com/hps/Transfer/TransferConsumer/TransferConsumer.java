package com.hps.Transfer.TransferConsumer;

import com.hps.DTOS.FeeDTO;
import com.hps.Transfer.mappers.FeeMapper;
import com.hps.Transfer.models.Transfer;
import com.hps.Transfer.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class TransferConsumer {

    private final TransferRepository transferRepository;

    @KafkaListener(topics = "fee-stream", groupId = "mygroup")
    public void handleFee(FeeDTO feeDTO) {
        log.info("Consuming the message from 'fee-stream' topic: {}", feeDTO);

        // Convert FeeDTO to Fee using MapStruct
        Transfer transfer = FeeMapper.INSTANCE.feeDTOToTransfer(feeDTO);

        // Save Fee to the database
        transferRepository.save(transfer);

        log.info("Saved Fee to the database: {}", transfer);
    }

}
