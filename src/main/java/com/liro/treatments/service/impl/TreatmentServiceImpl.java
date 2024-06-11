package com.liro.treatments.service.impl;

import com.liro.treatments.dto.TreatmentDTO;
import com.liro.treatments.dto.mappers.TreatmentMapper;
import com.liro.treatments.dto.responses.TreatmentResponse;
import com.liro.treatments.exceptions.ResourceNotFoundException;
import com.liro.treatments.model.dbentities.Treatment;
import com.liro.treatments.model.dbentities.TreatmentType;
import com.liro.treatments.repositories.TreatmentRepository;
import com.liro.treatments.repositories.TreatmentTypeRepository;
import com.liro.treatments.service.TreatmentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    private final TreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;
    private final TreatmentTypeRepository treatmentTypeRepository;


    public TreatmentServiceImpl(TreatmentRepository treatmentRepository,
                                TreatmentMapper treatmentMapper,
                                TreatmentTypeRepository treatmentTypeRepository) {
        this.treatmentRepository = treatmentRepository;
        this.treatmentMapper = treatmentMapper;
        this.treatmentTypeRepository = treatmentTypeRepository;
    }

    @Override
    public Page<TreatmentResponse> findAll(Pageable pageable) {
        return treatmentRepository.findAll(pageable).map(treatmentMapper::treatmentToTreatmentResponse);
    }

    @Override
    public TreatmentResponse findById(Long treatmentId) {
         Treatment treatment = treatmentRepository.findById(treatmentId)
                    .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with id: " + treatmentId));

          return treatmentMapper.treatmentToTreatmentResponse(treatment);
    }

    @Override
    public Page<TreatmentResponse> findAllByNameContaining(String nameContaining, Pageable pageable) {
        nameContaining = nameContaining.toLowerCase();

        return treatmentRepository.findAllByNameContaining(nameContaining, pageable)
                .map(treatmentMapper::treatmentToTreatmentResponse);
    }

    @Override
    public TreatmentResponse createTreatment(TreatmentDTO treatmentDTO) {
        if (treatmentDTO.getName() != null) {
            treatmentDTO.setName(treatmentDTO.getName().toLowerCase());
        }
        if (treatmentDTO.getFormalName() != null) {
            treatmentDTO.setFormalName(treatmentDTO.getFormalName().toLowerCase());
        }

        TreatmentType treatmentType = treatmentTypeRepository.findById(treatmentDTO.getTreatmentTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentType not found with id: " + treatmentDTO.getTreatmentTypeId()));

        Treatment treatment = treatmentMapper.treatmentDtoToTreatment(treatmentDTO);

        treatment.setTreatmentType(treatmentType);
        if (treatmentType.getTreatments() == null) treatmentType.setTreatments(new HashSet<>());
        treatmentType.getTreatments().add(treatment);

        return treatmentMapper.treatmentToTreatmentResponse(
                treatmentRepository.save(treatment)
        );    }
}
