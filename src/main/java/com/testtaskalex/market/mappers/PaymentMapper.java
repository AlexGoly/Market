package com.testtaskalex.market.mappers;

import com.testtaskalex.market.dtos.PaymentDto;
import com.testtaskalex.market.persistance.entities.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")

public interface PaymentMapper {
    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDto toDto(Payment payment);

}
