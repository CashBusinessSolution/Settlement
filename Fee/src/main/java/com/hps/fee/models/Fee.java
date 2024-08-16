package com.hps.fee.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Ou une autre stratégie si nécessaire
    private Long id;
    private BigDecimal feeAmount;
    private Long MerchantId;
    private String SettlementOption;
    private String FeeStructure;
    private BigDecimal TaxRate;
    private Long TransactionId;
    private BigDecimal Amount ;
    private String bankAccountNumber;
    private BigDecimal accountBalance;
}
