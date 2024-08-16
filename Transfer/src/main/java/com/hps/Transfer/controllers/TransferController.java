package com.hps.Transfer.controllers;

import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.Transfer.kafkaProducer.TransferJsonProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class TransferController {

    private final TransferJsonProducer transferJsonProducer;

    @PostMapping("/sendTransaction")
    public ResponseEntity<String> sendTransactionMessage(@RequestBody TransactionDTO transactionDTO) {
        transferJsonProducer.sendTransactionMessage(transactionDTO);
        return ResponseEntity.ok("Transaction message sent successfully");
    }

    @PostMapping("/sendMerchant")
    public ResponseEntity<String> sendMerchantMessage(@RequestBody MerchantDTO merchantDTO) {
        transferJsonProducer.sendMerchantMessage(merchantDTO);
        return ResponseEntity.ok("Merchant message sent successfully");
    }
    @PostMapping("/update")
    public ResponseEntity<String> updateMerchant(@RequestBody MerchantDTO merchantDTO) {
        transferJsonProducer.sendUpdateMerchantMessage(merchantDTO);
        return ResponseEntity.ok("Merchant message updated successfully");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteMerchant(@RequestBody MerchantDTO merchantDTO) {
        transferJsonProducer.sendDeleteMerchantMessage(merchantDTO);
        return ResponseEntity.ok("Merchant message deleted successfully");
    }
}
