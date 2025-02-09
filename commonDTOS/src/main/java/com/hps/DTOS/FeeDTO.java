package com.hps.DTOS;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDTO {
    private BigDecimal feeAmount;
    private Long merchantId;
    private String settlementOption;
    private String feeStructure;
    private BigDecimal taxRate;
    private Long transactionId;
    private BigDecimal amount ;
    private String bankAccountNumber;
    private BigDecimal accountBalance;
    private String cdfType;
    private LocalDate transactionDate;
}
