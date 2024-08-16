package com.hps.fee.mappers;

import com.hps.DTOS.FeeDTO;
import com.hps.fee.models.Fee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FeeMapper {
    FeeMapper INSTANCE = Mappers.getMapper(FeeMapper.class);
    FeeDTO feeToFeeDTO(Fee fee);
    Fee feeDTOToFee(FeeDTO feeDTO);
}
