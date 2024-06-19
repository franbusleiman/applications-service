package com.liro.applications.service;

import com.liro.applications.dto.ApplicationRecordDTO;
import com.liro.applications.dto.UserDTO;
import com.liro.applications.dto.responses.ApplicationRecordResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationRecordService {

    ApplicationRecordResponse findById(Long applicationRecordId, String token);

    Page<ApplicationRecordResponse> findAllByAnimalId(Long animalId, Pageable pageable, String token);

    Page<ApplicationRecordResponse> findAllByAnimalIdAndApplicationTypeId(Long animalId, Long applicationTypeId,
                                                                      Pageable pageable, String token);
    Page<ApplicationRecordResponse> findAllByAnimalIdAndApplicationId(Long animalId, Long applicationId,
                                                                  Pageable pageable, String token);

    ApplicationRecordResponse findLastByAnimalIdAndApplicationId(Long animalId, Long applicationId, String token);

    ApplicationRecordResponse findLastByAnimalIdAndApplicationTypeId(Long animalId, Long applicationTypeId, String token);

    ApplicationRecordResponse createApplicationRecord(ApplicationRecordDTO applicationRecordDTO, String token);


    void deleteApplicationRecord(Long applicationRecordId, String token);

}
