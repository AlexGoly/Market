package com.testtaskalex.mappers;

import com.testtaskalex.dtos.ItemDto;
import com.testtaskalex.persistance.entities.Item;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    ItemDto toDto(Item item);

}
