package com.hps.DTOS;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferDTO {
    private Long id;
    private BigDecimal feeAmount;
    private Long merchantId;
    private String settlementOption;
    private String feeStructure;
    private BigDecimal taxRate;
    private Long transactionId;
    private BigDecimal amount ;
    private String bankAccountNumber;
    private BigDecimal accountBalance;



}
