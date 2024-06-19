package com.liro.applications.service.impl;

import com.liro.applications.dto.ApplicationDTO;
import com.liro.applications.dto.mappers.ApplicationMapper;
import com.liro.applications.dto.responses.ApplicationResponse;
import com.liro.applications.exceptions.ResourceNotFoundException;
import com.liro.applications.model.dbentities.Application;
import com.liro.applications.model.dbentities.ApplicationType;
import com.liro.applications.repositories.ApplicationRepository;
import com.liro.applications.repositories.ApplicationTypeRepository;
import com.liro.applications.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final ApplicationTypeRepository applicationTypeRepository;


    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                ApplicationMapper applicationMapper,
                                ApplicationTypeRepository applicationTypeRepository) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.applicationTypeRepository = applicationTypeRepository;
    }

    @Override
    public Page<ApplicationResponse> findAll(Pageable pageable) {
        return applicationRepository.findAll(pageable).map(applicationMapper::applicationToApplicationResponse);
    }

    @Override
    public ApplicationResponse findById(Long applicationId) {
         Application application = applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

          return applicationMapper.applicationToApplicationResponse(application);
    }

    @Override
    public Page<ApplicationResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return applicationRepository.findAllByNameContaining(nameContaining, pageable)
                .map(applicationMapper::applicationToApplicationResponse);
    }

    @Override
    public ApplicationResponse createApplication(ApplicationDTO applicationDTO) {
        if (applicationDTO.getName() != null) {
            applicationDTO.setName(applicationDTO.getName().toLowerCase());
        }
        if (applicationDTO.getFormalName() != null) {
            applicationDTO.setFormalName(applicationDTO.getFormalName().toLowerCase());
        }

        ApplicationType applicationType = applicationTypeRepository.findById(applicationDTO.getApplicationTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationType not found with id: " + applicationDTO.getApplicationTypeId()));

        Application application = applicationMapper.applicationDtoToApplication(applicationDTO);

        application.setApplicationType(applicationType);
        if (applicationType.getApplications() == null) applicationType.setApplications(new HashSet<>());
        applicationType.getApplications().add(application);

        return applicationMapper.applicationToApplicationResponse(
                applicationRepository.save(application)
        );    }
}
