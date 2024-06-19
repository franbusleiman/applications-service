package com.liro.applications.dto.mappers;

import com.liro.applications.dto.ApplicationDTO;
import com.liro.applications.dto.responses.ApplicationResponse;
import com.liro.applications.model.dbentities.Application;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationMapper {

    @Mapping(target = "applicationTypeId", source = "applicationType.id")
    ApplicationResponse applicationToApplicationResponse(Application application);

    @Mapping(target = "applicationRecords", ignore = true)
    Application applicationDtoToApplication(ApplicationDTO applicationDTO);
}