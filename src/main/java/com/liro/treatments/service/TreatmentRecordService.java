package com.liro.treatments.service;

import com.liro.treatments.dto.TreatmentRecordDTO;
import com.liro.treatments.dto.UserDTO;
import com.liro.treatments.dto.responses.TreatmentRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TreatmentRecordService {

    TreatmentRecordResponse findById(Long treatmentRecordId, String token);

    Page<TreatmentRecordResponse> findAllByAnimalId(Long animalId, Pageable pageable, String token);

    Page<TreatmentRecordResponse> findAllByAnimalIdAndTreatmentTypeId(Long animalId, Long treatmentTypeId,
                                                                      Pageable pageable, String token);
    Page<TreatmentRecordResponse> findAllByAnimalIdAndTreatmentId(Long animalId, Long treatmentId,
                                                                  Pageable pageable, String token);

    TreatmentRecordResponse findLastByAnimalIdAndTreatmentId(Long animalId, Long treatmentId, String token);

    TreatmentRecordResponse findLastByAnimalIdAndTreatmentTypeId(Long animalId, Long treatmentTypeId, String token);

    TreatmentRecordResponse createTreatmentRecord(TreatmentRecordDTO treatmentRecordDTO, String token);


    void deleteTreatmentRecord(Long treatmentRecordId, String token);

}
