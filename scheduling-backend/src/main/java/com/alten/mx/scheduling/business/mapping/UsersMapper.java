package com.alten.mx.scheduling.business.mapping;

import org.mapstruct.Mapper;

import com.alten.mx.scheduling.persistance.dto.UsersDto;
import com.alten.mx.scheduling.persistance.entity.Users;

@Mapper(componentModel = "spring")
public interface UsersMapper {
    UsersDto toDto(Users users);
    Users toEntity(UsersDto usersDto);
}
