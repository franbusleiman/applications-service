package com.liro.treatments.service.impl;

import com.liro.treatments.dto.TreatmentTypeDTO;
import com.liro.treatments.dto.mappers.TreatmentTypeMapper;
import com.liro.treatments.dto.responses.TreatmentTypeResponse;
import com.liro.treatments.exceptions.ResourceNotFoundException;
import com.liro.treatments.model.dbentities.TreatmentType;
import com.liro.treatments.repositories.TreatmentTypeRepository;
import com.liro.treatments.service.TreatmentTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TreatmentTypeServiceImpl implements TreatmentTypeService {

    private final TreatmentTypeRepository treatmentTypeRepository;
    private final TreatmentTypeMapper treatmentTypeMapper;


    public TreatmentTypeServiceImpl(TreatmentTypeRepository treatmentTypeRepository,
                                    TreatmentTypeMapper treatmentTypeMapper) {
        this.treatmentTypeRepository = treatmentTypeRepository;
        this.treatmentTypeMapper = treatmentTypeMapper;
    }

    @Override
    public Page<TreatmentTypeResponse> findAll(Pageable pageable) {
        return treatmentTypeRepository.findAll(pageable).map(treatmentTypeMapper::treatmentTypeToTreatmentTypeResponse);
    }

    @Override
    public TreatmentTypeResponse findById(Long treatmentTypeId) {
        TreatmentType treatmentType = treatmentTypeRepository.findById(treatmentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentType not found with id: " + treatmentTypeId));

        return treatmentTypeMapper.treatmentTypeToTreatmentTypeResponse(treatmentType);    }

    @Override
    public Page<TreatmentTypeResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return treatmentTypeRepository.findAllByTypeNameContaining(nameContaining, pageable)
                .map(treatmentTypeMapper::treatmentTypeToTreatmentTypeResponse);    }

    @Override
    public TreatmentTypeResponse createTreatmentType(TreatmentTypeDTO treatmentTypeDTO) {
        if (treatmentTypeDTO.getTypeName() != null) {
            treatmentTypeDTO.setTypeName(treatmentTypeDTO.getTypeName().toLowerCase());
        }

        TreatmentType treatmentType = treatmentTypeMapper.treatmentTypeDtoToTreatmentType(treatmentTypeDTO);

        return treatmentTypeMapper.treatmentTypeToTreatmentTypeResponse(
                treatmentTypeRepository.save(treatmentType)
        );    }
}
