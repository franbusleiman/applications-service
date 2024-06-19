package com.liro.applications.dto.mappers;

import com.liro.applications.dto.ApplicationRecordDTO;
import com.liro.applications.dto.responses.ApplicationRecordResponse;
import com.liro.applications.model.dbentities.ApplicationRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ApplicationRecordMapper {

    @Mapping(target = "parentApplicationRecordId", source = "parentApplicationRecord.id")
    @Mapping(target = "applicationId", source = "application.id")
    ApplicationRecordResponse applicationRecordToApplicationRecordResponse(ApplicationRecord applicationRecord);

    @Mapping(target = "applicationRecordType", ignore = true)
    @Mapping(target = "application", ignore = true)
    @Mapping(target = "success", ignore = true)
    @Mapping(target = "parentApplicationRecord", ignore = true)
    @Mapping(target = "healthProblemRecords", ignore = true)
    @Mapping(target = "followupApplications", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "actionRecords", ignore = true)
    ApplicationRecord applicationRecordDtoToApplicationRecord(ApplicationRecordDTO applicationRecordDTO);
}