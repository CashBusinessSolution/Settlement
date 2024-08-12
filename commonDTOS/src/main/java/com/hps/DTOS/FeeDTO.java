package com.hps.DTOS;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeeDTO {
    private BigDecimal feeAmount;
    private Long MechantId;
    private String SettlementOption;
    private String FeeStructure;
    private BigDecimal TaxRate;
    private Long TransactionId;
    private BigDecimal Amount ;
    private String recipient;
}
