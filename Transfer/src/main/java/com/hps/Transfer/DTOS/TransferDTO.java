package com.hps.Transfer.DTOS;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("transferId")
    private String transferId;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("settlementAccount")
    private String settlementAccount;

    @JsonProperty("merchant")
    private String merchant;

    @JsonProperty("description")
    private String description;
}
