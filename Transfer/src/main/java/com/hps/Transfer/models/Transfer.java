package com.hps.Transfer.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transfer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal Amount;
    private BigDecimal feeAmount;
    private Long merchantId;
    private String settlementOption;
    private String feeStructure;
    private BigDecimal taxRate;
    private Long transactionId;
    private String bankAccountNumber;
    private BigDecimal accountBalance;
    private LocalDate transactionDate;
}
