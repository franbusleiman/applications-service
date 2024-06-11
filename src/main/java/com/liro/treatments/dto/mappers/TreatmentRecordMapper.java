package com.liro.treatments.dto.mappers;

import com.liro.treatments.dto.TreatmentRecordDTO;
import com.liro.treatments.dto.responses.TreatmentRecordResponse;
import com.liro.treatments.model.dbentities.TreatmentRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TreatmentRecordMapper {

    @Mapping(target = "parentTreatmentRecordId", source = "parentTreatmentRecord.id")
    @Mapping(target = "treatmentId", source = "treatment.id")
    TreatmentRecordResponse treatmentRecordToTreatmentRecordResponse(TreatmentRecord treatmentRecord);

    @Mapping(target = "treatmentRecordType", ignore = true)
    @Mapping(target = "treatment", ignore = true)
    @Mapping(target = "success", ignore = true)
    @Mapping(target = "parentTreatmentRecord", ignore = true)
    @Mapping(target = "healthProblemRecords", ignore = true)
    @Mapping(target = "followupTreatments", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "actionRecords", ignore = true)
    TreatmentRecord treatmentRecordDtoToTreatmentRecord(TreatmentRecordDTO treatmentRecordDTO);
}