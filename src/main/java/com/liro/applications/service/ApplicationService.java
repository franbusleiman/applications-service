package com.liro.applications.service;

import com.liro.applications.dto.ApplicationDTO;
import com.liro.applications.dto.responses.ApplicationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ApplicationService {

    Page<ApplicationResponse> findAll(Pageable pageable);

    ApplicationResponse findById(Long applicationId);

    Page<ApplicationResponse> findAllByNameContaining(String nameContaining, Pageable pageable);

    ApplicationResponse createApplication(ApplicationDTO applicationDTO);

}
