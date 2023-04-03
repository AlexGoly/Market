package com.testtaskalex.mappers;

import com.testtaskalex.dtos.UserDto;
import com.testtaskalex.persistance.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

}
