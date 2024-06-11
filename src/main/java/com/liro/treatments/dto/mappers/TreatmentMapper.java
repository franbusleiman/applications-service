package com.liro.treatments.dto.mappers;

import com.liro.treatments.dto.TreatmentDTO;
import com.liro.treatments.dto.responses.TreatmentResponse;
import com.liro.treatments.model.dbentities.Treatment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreatmentMapper {

    @Mapping(target = "treatmentTypeId", source = "treatmentType.id")
    TreatmentResponse treatmentToTreatmentResponse(Treatment treatment);

    @Mapping(target = "actions", ignore = true)
    @Mapping(target = "treatmentRecords", ignore = true)
    Treatment treatmentDtoToTreatment(TreatmentDTO treatmentDTO);
}