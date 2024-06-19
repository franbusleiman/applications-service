package com.liro.applications.service.impl;

import com.liro.applications.config.FeignAnimalClient;
import com.liro.applications.dto.ApplicationRecordDTO;
import com.liro.applications.dto.UserDTO;
import com.liro.applications.dto.mappers.ApplicationRecordMapper;
import com.liro.applications.dto.responses.ApplicationRecordResponse;
import com.liro.applications.exceptions.ResourceNotFoundException;
import com.liro.applications.model.dbentities.Application;
import com.liro.applications.model.dbentities.ApplicationRecord;
import com.liro.applications.repositories.ApplicationRecordRepository;
import com.liro.applications.repositories.ApplicationRepository;
import com.liro.applications.service.ApplicationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.liro.applications.utils.Util.getUser;

@Service
public class ApplicationRecordServiceImpl implements ApplicationRecordService {

    private final ApplicationRecordRepository applicationRecordRepository;
    private final ApplicationRepository applicationRepository;
    private final ApplicationRecordMapper applicationRecordMapper;

    private final FeignAnimalClient feignAnimalClient;

    @Autowired
    public ApplicationRecordServiceImpl(ApplicationRecordRepository applicationRecordRepository,
                                      ApplicationRepository applicationRepository,
                                      ApplicationRecordMapper applicationRecordMapper,
                                      FeignAnimalClient feignAnimalClient) {
        this.applicationRecordRepository = applicationRecordRepository;
        this.applicationRepository = applicationRepository;
        this.applicationRecordMapper = applicationRecordMapper;
        this.feignAnimalClient = feignAnimalClient;
    }



    @Override
    public ApplicationRecordResponse findById(Long applicationRecordId, String token) {
        ApplicationRecord applicationRecord = applicationRecordRepository.findById(applicationRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationRecord not found with id: " + applicationRecordId));

        feignAnimalClient.hasPermissions(applicationRecord.getAnimalId() , false, false, true, false, token);

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(applicationRecord);    }

    @Override
    public Page<ApplicationRecordResponse> findAllByAnimalId(Long animalId, Pageable pageable, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        return applicationRecordRepository.findAllByAnimalId(animalId, pageable)
                .map(applicationRecordMapper::applicationRecordToApplicationRecordResponse);    }

    @Override
    public Page<ApplicationRecordResponse> findAllByAnimalIdAndApplicationTypeId(Long animalId, Long applicationTypeId, Pageable pageable, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        return applicationRecordRepository.findAllByAnimalIdAndApplicationApplicationTypeId(animalId, applicationTypeId, pageable)
                .map(applicationRecordMapper::applicationRecordToApplicationRecordResponse);    }

    @Override
    public Page<ApplicationRecordResponse> findAllByAnimalIdAndApplicationId(Long animalId, Long applicationId, Pageable pageable, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        return applicationRecordRepository.findAllByAnimalIdAndApplicationId(animalId, applicationId, pageable)
                .map(applicationRecordMapper::applicationRecordToApplicationRecordResponse);    }

    @Override
    public ApplicationRecordResponse findLastByAnimalIdAndApplicationId(Long animalId, Long applicationId, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        ApplicationRecord applicationRecord = applicationRecordRepository
                .findLastByAnimalIdAndApplicationId(animalId, applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationRecord not found with animalId: " + animalId
                        + " and applicationId: " + applicationId));

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(applicationRecord);    }

    @Override
    public ApplicationRecordResponse findLastByAnimalIdAndApplicationTypeId(Long animalId, Long applicationTypeId, String token) {
        feignAnimalClient.hasPermissions(animalId, false, false, true, false, token);

        ApplicationRecord applicationRecord = applicationRecordRepository
                .findLastByAnimalIdAndApplicationTypeId(animalId, applicationTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationRecord not found with animalId: " + animalId
                        + " and applicationTypeId: " + applicationTypeId));

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(applicationRecord);
    }

    @Override
    public ApplicationRecordResponse createApplicationRecord(ApplicationRecordDTO applicationRecordDTO, String token) {

        feignAnimalClient.hasPermissions(applicationRecordDTO.getAnimalId(), true, false, true, false, token);

        ApplicationRecord applicationRecord = applicationRecordMapper.applicationRecordDtoToApplicationRecord(applicationRecordDTO);

        Application application = applicationRepository.findById(applicationRecordDTO.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: "
                        + applicationRecordDTO.getApplicationId()));

        applicationRecord.setApplication(application);

        UserDTO userDTO =  getUser(token);
//        // TODO: Change to call the vet validation endpoint to be created in the userService
        if (userDTO.getRoles().contains("VET")) {
            applicationRecord.setValid(true);
            applicationRecord.setVetProfileId(userDTO.getId());
        }

        return applicationRecordMapper.applicationRecordToApplicationRecordResponse(
                applicationRecordRepository.save(applicationRecord)
        );    }

    @Override
    public void deleteApplicationRecord(Long applicationRecordId, String token) {

        ApplicationRecord record =  applicationRecordRepository.findById(applicationRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("ApplicationRecord not found with id: " + applicationRecordId));

        feignAnimalClient.hasPermissions(record.getAnimalId(), true, false, true,false , token);

        applicationRecordRepository.deleteById(applicationRecordId);
    }
}
