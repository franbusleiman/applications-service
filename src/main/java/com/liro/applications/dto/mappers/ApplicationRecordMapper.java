package com.liro.applications.dto.mappers;

import com.liro.applications.dto.ApplicationRecordDTO;
import com.liro.applications.dto.responses.ApplicationRecordResponse;
import com.liro.applications.model.dbentities.ApplicationRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationRecordMapper {

    @Mapping(target = "applicationId", source = "application.id")
    ApplicationRecordResponse applicationRecordToApplicationRecordResponse(ApplicationRecord applicationRecord);

    @Mapping(target = "application", ignore = true)
    ApplicationRecord applicationRecordDtoToApplicationRecord(ApplicationRecordDTO applicationRecordDTO);
}