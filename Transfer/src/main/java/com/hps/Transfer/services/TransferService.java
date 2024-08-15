package com.hps.Transfer.services;

import com.hps.DTOS.TransferDTO;
import com.hps.Transfer.models.Transfer;
import com.hps.Transfer.repositories.TransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferService {

    private final TransferRepository transferRepository;

    public Transfer saveTransfer(Transfer transfer) {
        return transferRepository.save(transfer);
    }


}
