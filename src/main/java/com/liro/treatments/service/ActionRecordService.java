package com.liro.treatments.service;

import com.liro.treatments.dto.ActionRecordDTO;
import com.liro.treatments.dto.responses.ActionRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionRecordService {

    Page<ActionRecordResponse> findAllByTreatmentRecordId(Long treatmentRecordId, Pageable pageable, String token);

    ActionRecordResponse findById(Long actionRecordId, String token);

    ActionRecordResponse createActionRecord(ActionRecordDTO actionRecordTypeDTO);

    void deleteActionRecord(Long actionRecordId, String token);

}
