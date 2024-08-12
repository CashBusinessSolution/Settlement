package com.hps.fee.kafkaProducer;

import com.hps.DTOS.FeeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeeProducer {

    private final KafkaTemplate<String, FeeDTO> kafkaTemplate;

    private static final String TOPIC = "fee-stream";

    public void sendFee(FeeDTO feeDTO) {
        kafkaTemplate.send(TOPIC, feeDTO);
    }
}
