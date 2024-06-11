package com.liro.treatments.dto.mappers;

import com.liro.treatments.dto.TreatmentTypeDTO;
import com.liro.treatments.dto.responses.TreatmentTypeResponse;
import com.liro.treatments.model.dbentities.TreatmentType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreatmentTypeMapper {

    TreatmentTypeResponse treatmentTypeToTreatmentTypeResponse(TreatmentType treatmentType);

    @Mapping(target = "treatments", ignore = true)
    TreatmentType treatmentTypeDtoToTreatmentType(TreatmentTypeDTO treatmentTypeDTO);
}