package com.hps.fee.mappers;

import com.hps.DTOS.MerchantDTO;
import com.hps.fee.models.Merchant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MerchantMapper {
    Merchant toEntity(MerchantDTO dto);
    MerchantDTO toDTO(Merchant entity);
}
