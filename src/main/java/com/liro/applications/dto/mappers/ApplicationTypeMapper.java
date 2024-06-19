package com.liro.applications.dto.mappers;

import com.liro.applications.dto.ApplicationTypeDTO;
import com.liro.applications.dto.responses.ApplicationTypeResponse;
import com.liro.applications.model.dbentities.ApplicationType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationTypeMapper {

    ApplicationTypeResponse applicationTypeToApplicationTypeResponse(ApplicationType applicationType);

    @Mapping(target = "applications", ignore = true)
    ApplicationType applicationTypeDtoToApplicationType(ApplicationTypeDTO applicationTypeDTO);
}