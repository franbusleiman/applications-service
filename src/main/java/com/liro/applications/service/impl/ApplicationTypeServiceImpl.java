package com.liro.applications.service.impl;

import com.liro.applications.dto.ApplicationTypeDTO;
import com.liro.applications.dto.mappers.ApplicationTypeMapper;
import com.liro.applications.dto.responses.ApplicationTypeResponse;
import com.liro.applications.exceptions.ResourceNotFoundException;
import com.liro.applications.model.dbentities.ApplicationType;
import com.liro.applications.repositories.ApplicationTypeRepository;
import com.liro.applications.service.ApplicationTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ApplicationTypeServiceImpl implements ApplicationTypeService {

    private final ApplicationTypeRepository applicationTypeRepository;
    private final ApplicationTypeMapper applicationTypeMapper;


    public ApplicationTypeServiceImpl(ApplicationTypeRepository applicationTypeRepository,
                                    ApplicationTypeMapper applicationTypeMapper) {
        this.applicationTypeRepository = applicationTypeRepository;
        this.applicationTypeMapper = applicationTypeMapper;
    }

    @Override
    public Page<ApplicationTypeResponse> findAll(Pageable pageable) {
        return applicationTypeRepository.findAll(pageable).map(applicationTypeMapper::applicationTypeToApplicationTypeResponse);
    }

    @Override
    public ApplicationTypeResponse findById(Long applicationTypeId) {
        ApplicationType applicationType = applicationTypeRepository.findById(applicationTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationType not found with id: " + applicationTypeId));

        return applicationTypeMapper.applicationTypeToApplicationTypeResponse(applicationType);    }

    @Override
    public Page<ApplicationTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return applicationTypeRepository.findAllByTypeNameContaining(nameContaining, pageable)
                .map(applicationTypeMapper::applicationTypeToApplicationTypeResponse);    }

    @Override
    public ApplicationTypeResponse createApplicationType(ApplicationTypeDTO applicationTypeDTO) {
        if (applicationTypeDTO.getTypeName() != null) {
            applicationTypeDTO.setTypeName(applicationTypeDTO.getTypeName().toLowerCase());
        }

        ApplicationType applicationType = applicationTypeMapper.applicationTypeDtoToApplicationType(applicationTypeDTO);

        return applicationTypeMapper.applicationTypeToApplicationTypeResponse(
                applicationTypeRepository.save(applicationType)
        );    }
}
