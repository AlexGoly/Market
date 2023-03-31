package com.testtaskalex.market.mappers;

import com.testtaskalex.market.dtos.OrderDto;
import com.testtaskalex.market.persistance.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);
}
