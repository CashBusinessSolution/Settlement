package com.hps.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {


    private String transferId;

    private BigDecimal amount;

    private String settlementAccount;

    private String merchant;

    @JsonProperty("description")
    private String description;
}
