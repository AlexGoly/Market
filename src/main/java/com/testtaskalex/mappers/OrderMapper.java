package com.testtaskalex.mappers;

import com.testtaskalex.dtos.OrderDto;
import com.testtaskalex.persistance.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Order order);
}
