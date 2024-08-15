package com.hps.Transfer.mappers;

import com.hps.DTOS.FeeDTO;
import com.hps.Transfer.models.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeeMapper {
    FeeMapper INSTANCE = Mappers.getMapper(FeeMapper.class);

    Transfer feeDTOToTransfer(FeeDTO feeDTO);

    FeeDTO transferToFeeDTO(Transfer transfer);
}
