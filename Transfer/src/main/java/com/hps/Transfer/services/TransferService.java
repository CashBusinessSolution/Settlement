package com.hps.Transfer.services;

import com.hps.DTOS.TransferDTO;
import com.hps.Transfer.models.Transfer;
import com.hps.Transfer.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransferService {

    @Autowired
    private final TransferRepository transferRepository;


    public void updateAccountBalance(Long Id, BigDecimal amount) {
        Transfer transfer = transferRepository.findById(Id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
        BigDecimal updatedBalance = transfer.getAccountBalance().add(amount);
        transfer.setAccountBalance(updatedBalance);
        transferRepository.save(transfer);
        log.info("Updated account balance Id {}: {}", Id, updatedBalance);
    }



}
