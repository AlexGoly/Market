package com.testtaskalex.market.mappers;

import com.testtaskalex.market.dtos.ItemDto;
import com.testtaskalex.market.persistance.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDto toDto(Item item);

}
