package com.hps.fee.services;

import com.hps.fee.models.Fee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeeService {
    @Autowired
    private com.hps.fee.repositories.FeeRepository FeeRepository;

    public Fee createFees(Fee fee) {
        return FeeRepository.save(fee);
    }

    public List<Fee> getAllFees() {
        return FeeRepository.findAll();
    }

    public Fee getFeesById(Long id) {
        return FeeRepository.findById(id).orElse(null);
    }

    public void deleteFees(Long id) {
        FeeRepository.deleteById(id);
    }


    public Fee calculateFee(BigDecimal amount, String description) {
        BigDecimal feeAmount = amount.multiply(BigDecimal.valueOf(0.02)); // Exemple de calcul de frais
        Fee fee = Fee.builder()   // Utilisation de Fee pour la construction
                .amount(feeAmount)
                .description(description)
                .build();
        return FeeRepository.save(fee);  // Sauvegarde via feeRepository
    }

}