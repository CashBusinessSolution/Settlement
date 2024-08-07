package com.hps.fee.services;

import com.hps.fee.models.Fee;
import com.hps.fee.repositories.FeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FeeService {

    // Injection de la dépendance avec @RequiredArgsConstructor
    private final FeeRepository feeRepository;

    // Création et sauvegarde des frais
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

    // Calcul des frais
    public Fee calculateFee(BigDecimal amount) {
        // Calcul du montant des frais
        BigDecimal feeAmount = amount.multiply(BigDecimal.valueOf(0.02)); // Exemple de calcul de frais

        // Création de l'objet Fee
        Fee fee = Fee.builder()
                .amount(amount) // Le montant initial
                .fee(feeAmount) // Le montant des frais calculés
                .build();

        // Sauvegarde de l'objet Fee et retour de l'objet sauvegardé
        return feeRepository.save(fee);
    }
}
