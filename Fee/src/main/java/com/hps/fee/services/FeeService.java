package com.hps.fee.services;

import com.hps.DTOS.MerchantDTO;
import com.hps.DTOS.TransactionDTO;
import com.hps.fee.mappers.MerchantMapper;
import com.hps.fee.models.Fee;
import com.hps.fee.models.Merchant;
import com.hps.fee.repositories.FeeRepository;
import com.hps.fee.repositories.MerchantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeeService {


    private final MerchantRepository merchantRepository;
    private final FeeRepository feeRepository;
    private final MerchantMapper merchantMapper;

    public void saveMerchant(MerchantDTO merchantDTO) {
        Merchant merchant = merchantMapper.toEntity(merchantDTO);
        merchantRepository.save(merchant);
    }

    public void processTransaction(TransactionDTO transactionDTO) {
        Fee fee = new Fee();
        fee.setTransactionId(transactionDTO.getTransactionId());
        fee.setAmount(transactionDTO.getAmount());
        fee.setRecipient(transactionDTO.getRecipient());
        feeRepository.save(fee);
    }

    public void processMerchant(MerchantDTO merchantDTO) {
        Fee fee = new Fee();
        fee.setMerchantId(merchantDTO.getMerchantId());
        fee.setSettlementOption(merchantDTO.getSettlementOption());
        fee.setFeeStructure(merchantDTO.getFeeStructure());
        fee.setTaxRate(merchantDTO.getTaxRate());

        feeRepository.save(fee);

    }
    public Fee createFees(Fee fee) {
        return feeRepository.save(fee);
    }

    // Récupération de tous les frais
    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    // Récupération des frais par ID
    public Fee getFeesById(Long id) {
        return feeRepository.findById(id).orElse(null);
    }

    // Suppression des frais par ID
    public void deleteFees(Long id) {
        feeRepository.deleteById(id);
    }

    public Fee calculateFee(BigDecimal amount, BigDecimal taxRate) {
        BigDecimal feeAmount = amount.multiply(taxRate);

        // Création de l'objet Fee
        Fee fee = Fee.builder()
                .Amount(amount) // Le montant initial
                .feeAmount(feeAmount) // Le montant des frais calculés
                .build();

        // Sauvegarde de l'objet Fee et retour de l'objet sauvegardé
        return feeRepository.save(fee);
    }
}
