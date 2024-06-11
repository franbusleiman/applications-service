package com.liro.treatments.service;

import com.liro.treatments.dto.TreatmentTypeDTO;
import com.liro.treatments.dto.responses.TreatmentTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TreatmentTypeService {

    Page<TreatmentTypeResponse> findAll(Pageable pageable);

    TreatmentTypeResponse findById(Long treatmentTypeId);

    Page<TreatmentTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    TreatmentTypeResponse createTreatmentType(TreatmentTypeDTO treatmentTypeDTO);

}
