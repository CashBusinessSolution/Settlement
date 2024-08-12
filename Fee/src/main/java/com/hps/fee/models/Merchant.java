package com.hps.fee.models;

import java.math.BigDecimal;

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
}
