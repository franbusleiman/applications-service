package com.liro.treatments.service;

import com.liro.treatments.dto.ActionDTO;
import com.liro.treatments.dto.responses.ActionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActionService {

    Page<ActionResponse> findAllByTreatmentId(Long treatmentId, Pageable pageable);

    ActionResponse findById(Long actionId);

    ActionResponse createAction(ActionDTO actionTypeDTO);

    void deleteAction(Long actionId);

}
