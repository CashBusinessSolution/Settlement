package com.hps.Transfer.controllers;

import com.hps.DTOS.TransferDTO;
import com.hps.Transfer.kafkaProducer.TransferJsonProducer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/transfer")
@RequiredArgsConstructor
public class TransferController {

    private final TransferJsonProducer transferJsonProducer;

    @PostMapping("/json")
    public ResponseEntity<String> sendJsonMessage(@RequestBody TransferDTO message) {
        transferJsonProducer.sendMessage(message);
        return ResponseEntity.ok("Message queued successfully as Json");
    }
}
