package com.liro.treatments.service;

import com.liro.treatments.dto.TreatmentDTO;
import com.liro.treatments.dto.responses.TreatmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TreatmentService {

    Page<TreatmentResponse> findAll(Pageable pageable);

    TreatmentResponse findById(Long treatmentId);

    Page<TreatmentResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    TreatmentResponse createTreatment(TreatmentDTO treatmentDTO);

}
