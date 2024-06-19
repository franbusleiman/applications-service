package com.liro.applications.service;

import com.liro.applications.dto.ApplicationTypeDTO;
import com.liro.applications.dto.responses.ApplicationTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationTypeService {

    Page<ApplicationTypeResponse> findAll(Pageable pageable);

    ApplicationTypeResponse findById(Long applicationTypeId);

    Page<ApplicationTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    ApplicationTypeResponse createApplicationType(ApplicationTypeDTO applicationTypeDTO);

}
