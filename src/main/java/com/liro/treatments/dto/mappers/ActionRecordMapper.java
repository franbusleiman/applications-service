package com.liro.treatments.dto.mappers;

import com.liro.treatments.dto.ActionRecordDTO;
import com.liro.treatments.dto.responses.ActionRecordResponse;
import com.liro.treatments.model.dbentities.ActionRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActionRecordMapper {

    @Mapping(target = "treatmentRecordId", source = "treatmentRecord.id")
    @Mapping(target = "actionId", source = "action.id")
    ActionRecordResponse actionRecordToActionRecordResponse(ActionRecord actionRecord);


    @Mapping(target = "vetProfileId", ignore = true)
    @Mapping(target = "valid", ignore = true)
    @Mapping(target = "treatmentRecord", ignore = true)
    @Mapping(target = "action", ignore = true)
    ActionRecord actionRecordDtoToActionRecord(ActionRecordDTO actionRecordDTO);
}