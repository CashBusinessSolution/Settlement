package com.hps.fee.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
public class Merchant {

    @Id
    private Long MerchantId;
    private String settlementOption;
    private String feeStructure;
    private BigDecimal taxRate;
    private String bankAccountNumber;
    private BigDecimal accountBalance;
    private String cdfType;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private LocalDate LastTransactionDate;
}
