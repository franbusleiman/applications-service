package com.liro.treatments.service.impl;

import com.liro.treatments.config.FeignAnimalClient;
import com.liro.treatments.dto.TreatmentRecordDTO;
import com.liro.treatments.dto.UserDTO;
import com.liro.treatments.dto.mappers.TreatmentRecordMapper;
import com.liro.treatments.dto.responses.TreatmentRecordResponse;
import com.liro.treatments.exceptions.ResourceNotFoundException;
import com.liro.treatments.model.dbentities.Treatment;
import com.liro.treatments.model.dbentities.TreatmentRecord;
import com.liro.treatments.repositories.TreatmentRecordRepository;
import com.liro.treatments.repositories.TreatmentRepository;
import com.liro.treatments.service.TreatmentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.liro.treatments.utils.Util.getUser;

@Service
public class TreatmentRecordServiceImpl implements TreatmentRecordService {

    private final TreatmentRecordRepository treatmentRecordRepository;
    private final TreatmentRepository treatmentRepository;
    private final TreatmentRecordMapper treatmentRecordMapper;

    private final FeignAnimalClient feignAnimalClient;

    @Autowired
    public TreatmentRecordServiceImpl(TreatmentRecordRepository treatmentRecordRepository,
                                      TreatmentRepository treatmentRepository,
                                      TreatmentRecordMapper treatmentRecordMapper,
                                      FeignAnimalClient feignAnimalClient) {
        this.treatmentRecordRepository = treatmentRecordRepository;
        this.treatmentRepository = treatmentRepository;
        this.treatmentRecordMapper = treatmentRecordMapper;
        this.feignAnimalClient = feignAnimalClient;
    }



    @Override
    public TreatmentRecordResponse findById(Long treatmentRecordId, String token) {
        TreatmentRecord treatmentRecord = treatmentRecordRepository.findById(treatmentRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentRecord not found with id: " + treatmentRecordId));

        feignAnimalClient.hasPermissions(treatmentRecord.getAnimalId() , false, false, true, false, token);

        return treatmentRecordMapper.treatmentRecordToTreatmentRecordResponse(treatmentRecord);    }

    @Override
    public Page<TreatmentRecordResponse> findAllByAnimalId(Long animalId, Pageable pageable, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        return treatmentRecordRepository.findAllByAnimalId(animalId, pageable)
                .map(treatmentRecordMapper::treatmentRecordToTreatmentRecordResponse);    }

    @Override
    public Page<TreatmentRecordResponse> findAllByAnimalIdAndTreatmentTypeId(Long animalId, Long treatmentTypeId, Pageable pageable, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        return treatmentRecordRepository.findAllByAnimalIdAndTreatmentTreatmentTypeId(animalId, treatmentTypeId, pageable)
                .map(treatmentRecordMapper::treatmentRecordToTreatmentRecordResponse);    }

    @Override
    public Page<TreatmentRecordResponse> findAllByAnimalIdAndTreatmentId(Long animalId, Long treatmentId, Pageable pageable, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        return treatmentRecordRepository.findAllByAnimalIdAndTreatmentId(animalId, treatmentId, pageable)
                .map(treatmentRecordMapper::treatmentRecordToTreatmentRecordResponse);    }

    @Override
    public TreatmentRecordResponse findLastByAnimalIdAndTreatmentId(Long animalId, Long treatmentId, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        TreatmentRecord treatmentRecord = treatmentRecordRepository
                .findLastByAnimalIdAndTreatmentId(animalId, treatmentId)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentRecord not found with animalId: " + animalId
                        + " and treatmentId: " + treatmentId));

        return treatmentRecordMapper.treatmentRecordToTreatmentRecordResponse(treatmentRecord);    }

    @Override
    public TreatmentRecordResponse findLastByAnimalIdAndTreatmentTypeId(Long animalId, Long treatmentTypeId, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        TreatmentRecord treatmentRecord = treatmentRecordRepository
                .findLastByAnimalIdAndTreatmentTypeId(animalId, treatmentTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentRecord not found with animalId: " + animalId
                        + " and treatmentTypeId: " + treatmentTypeId));

        return treatmentRecordMapper.treatmentRecordToTreatmentRecordResponse(treatmentRecord);
    }

    @Override
    public TreatmentRecordResponse createTreatmentRecord(TreatmentRecordDTO treatmentRecordDTO, String token) {

        feignAnimalClient.hasPermissions(treatmentRecordDTO.getAnimalId(), true, false, true, false, token);

        TreatmentRecord treatmentRecord = treatmentRecordMapper.treatmentRecordDtoToTreatmentRecord(treatmentRecordDTO);

        Treatment treatment = treatmentRepository.findById(treatmentRecordDTO.getTreatmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with id: "
                        + treatmentRecordDTO.getTreatmentId()));

        treatmentRecord.setTreatment(treatment);

        UserDTO userDTO =  getUser(token);
//        // TODO: Change to call the vet validation endpoint to be created in the userService
        if (userDTO.getRoles().contains("VET")) {
            treatmentRecord.setValid(true);
            treatmentRecord.setVetProfileId(userDTO.getId());
        }

        return treatmentRecordMapper.treatmentRecordToTreatmentRecordResponse(
                treatmentRecordRepository.save(treatmentRecord)
        );    }

    @Override
    public void deleteTreatmentRecord(Long treatmentRecordId, String token) {

        TreatmentRecord record =  treatmentRecordRepository.findById(treatmentRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentRecord not found with id: " + treatmentRecordId));

        feignAnimalClient.hasPermissions(record.getAnimalId(), true, false, true,false , token);

        treatmentRecordRepository.deleteById(treatmentRecordId);
    }
}
