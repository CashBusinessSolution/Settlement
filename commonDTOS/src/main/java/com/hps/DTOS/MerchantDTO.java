package com.hps.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDTO {
    private Long MerchantId;
    private String SettlementOption;
    private String FeeStructure;
    private BigDecimal TaxRate;
    private String bankAccountNumber;
    private BigDecimal accountBalance;
    private String cdfType;
    private LocalDate LastTransactionDate;
}
