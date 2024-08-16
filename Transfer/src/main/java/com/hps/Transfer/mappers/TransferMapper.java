package com.hps.Transfer.mappers;

import com.hps.DTOS.TransferDTO;
import com.hps.Transfer.models.Transfer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransferMapper {
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

    TransferDTO transferToTransferDTO(Transfer transfer);

    Transfer transferDTOToTransfer(TransferDTO transferDTO);
}
