package com.hps.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor

public class TransactionDTO {
    private Long transactionId;
    private Long merchantId;
    private BigDecimal amount ;
    private String recipient;
}
