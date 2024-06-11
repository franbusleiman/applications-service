package com.liro.treatments.dto.mappers;

import com.liro.treatments.dto.ActionDTO;
import com.liro.treatments.dto.responses.ActionResponse;
import com.liro.treatments.model.dbentities.Action;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    @Mapping(target = "treatmentId", source = "treatment.id")
    ActionResponse actionToActionResponse(Action action);


    @Mapping(target = "treatment", ignore = true)
    @Mapping(target = "actionRecords", ignore = true)
    Action actionDtoToAction(ActionDTO actionDTO);
}