package com.hps.DTOS;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private BigDecimal feeAmount;
    private Long merchantId;
    private String settlementOption;
    private String feeStructure;
    private BigDecimal taxRate;
    private Long transactionId;
    private BigDecimal amount ;
    private String recipient;
}
