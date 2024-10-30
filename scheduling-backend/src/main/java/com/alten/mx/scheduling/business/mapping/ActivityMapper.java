package com.alten.mx.scheduling.business.mapping;

import org.mapstruct.Mapper;

import com.alten.mx.scheduling.persistance.dto.ActivityDto;
import com.alten.mx.scheduling.persistance.entity.Activity;

@Mapper(componentModel = "spring")
public interface ActivityMapper {

    ActivityDto toDto(Activity activity);
    Activity toEntity(ActivityDto activityDto);
    
}
