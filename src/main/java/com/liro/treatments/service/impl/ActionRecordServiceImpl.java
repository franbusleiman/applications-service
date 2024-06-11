package com.liro.treatments.service.impl;

import com.liro.treatments.config.FeignAnimalClient;
import com.liro.treatments.dto.ActionRecordDTO;
import com.liro.treatments.dto.mappers.ActionRecordMapper;
import com.liro.treatments.dto.responses.ActionRecordResponse;
import com.liro.treatments.exceptions.ResourceNotFoundException;
import com.liro.treatments.model.dbentities.Action;
import com.liro.treatments.model.dbentities.ActionRecord;
import com.liro.treatments.model.dbentities.TreatmentRecord;
import com.liro.treatments.repositories.ActionRecordRepository;
import com.liro.treatments.repositories.ActionRepository;
import com.liro.treatments.repositories.TreatmentRecordRepository;
import com.liro.treatments.service.ActionRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ActionRecordServiceImpl implements ActionRecordService {

    private final ActionRecordRepository actionRecordRepository;
    private final TreatmentRecordRepository treatmentRecordRepository;
    private final ActionRepository actionRepository;
    private final ActionRecordMapper actionRecordMapper;

    private final FeignAnimalClient feignAnimalClient;


    @Autowired
    public ActionRecordServiceImpl(ActionRecordRepository actionRecordRepository,
                                   TreatmentRecordRepository treatmentRecordRepository,
                                   ActionRepository actionRepository,
                                   ActionRecordMapper actionRecordMapper,
                                   FeignAnimalClient feignAnimalClient) {
        this.actionRecordRepository = actionRecordRepository;
        this.treatmentRecordRepository = treatmentRecordRepository;
        this.actionRepository = actionRepository;
        this.actionRecordMapper = actionRecordMapper;
        this.feignAnimalClient = feignAnimalClient;
    }

    @Override
    public Page<ActionRecordResponse> findAllByTreatmentRecordId(Long treatmentRecordId, Pageable pageable, String token) {
        TreatmentRecord treatmentRecord = treatmentRecordRepository.findById(treatmentRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentRecord not found with id: " + treatmentRecordId));

        feignAnimalClient.hasPermissions(treatmentRecord.getAnimalId(), false, false, true, false, token);

        return actionRecordRepository.findAllByTreatmentRecordId(treatmentRecordId, pageable)
                .map(actionRecordMapper::actionRecordToActionRecordResponse);
    }

    @Override
    public ActionRecordResponse findById(Long actionRecordId, String token) {

        ActionRecord actionRecord = actionRecordRepository.findById(actionRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("ActionRecord not found with id: " + actionRecordId));

        feignAnimalClient.hasPermissions(actionRecord.getTreatmentRecord().getAnimalId(),
                false, false, true, false, token);

        return actionRecordMapper.actionRecordToActionRecordResponse(actionRecord);
    }

    @Override
    public ActionRecordResponse createActionRecord(ActionRecordDTO actionRecordDTO) {

        feignAnimalClient.hasPermissions(actionRecordDTO.get)

        TreatmentRecord treatmentRecord = treatmentRecordRepository.findById(actionRecordDTO.getTreatmentRecordId())
                .orElseThrow(() -> new ResourceNotFoundException("TreatmentRecord not found with id: " + actionRecordDTO.getTreatmentRecordId()));

        Action action = actionRepository.findById(actionRecordDTO.getActionId())
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + actionRecordDTO.getActionId()));

        ActionRecord actionRecord = actionRecordMapper.actionRecordDtoToActionRecord(actionRecordDTO);
        actionRecord.setTreatmentRecord(treatmentRecord);
        if (treatmentRecord.getActionRecords() == null) treatmentRecord.setActionRecords(new HashSet<>());
        treatmentRecord.getActionRecords().add(actionRecord);

//        actionRecord.setAction(action);
//        if (action.getActionRecords() == null) action.setActionRecords(new HashSet<>());
//        action.getActionRecords().add(actionRecord);
//
//        // TODO: Change to call the vet validation endpoint to be created in the userService
//        if (user.getVetProfileId() != null) {
//            actionRecord.setValid(true);
//            actionRecord.setVetProfileId(user.getVetProfileId());
//        }
//
//        if (actionRecord.getActionsMedicines() != null) actionRecord.getActionsMedicines().setActionRecord(actionRecord);
//        if (actionRecord.getActionsProcedures() != null) actionRecord.getActionsProcedures().setActionRecord(actionRecord);

        // TODO: Chequear existencia de los id


        // TODO: Validations for custom duration if not null - It's needed to check the value with the custom duration
        //  of the treatmentRecord if not null, if null use the duration of the treatment
        //  Validations for the start-end dates with the duration in days, and with the priority numbers
        //  Check: https://git.sauken.com.ar/pethc/backend-development/tasks/-/issues/24  (Para actionRecord record)
        //  Controlar que cuando se añade un medicamento o procedimiento a un tratamiento que la fecha de aplicacion no
        //  sea superior al siguiente priorityNumber y en caso de existir un priorityNumber igual que sus fechas sean
        //  iguales, tambien que lo que se añada tenga una fecha dentro del inicio y fin del tratamiento.

        //  Esto sera útil para tratamientos para "problemas de salud" de tipo alergias u otros, así como también
        //  para ciclos menstruales y demás
        //  Existiran tratamientos ciclicos y acciones ciclicas.
        //  Dentro de acciones ciclicas la primera en priority no puede tener fecha de inicio o fin fuera del rango de
        //  fechas del tratamiento, las acciones no deben tener una fecha de inicio anterior a la fecha de fin maxima
        //  del priority anterior, y no deben tener una fecha de fin mayor a la menor fecha de inicio mas pequeña
        //  del priority siguiente.

        /*Integer maxPriorityNumber = null;
        int durationSum = (actionRecord.getCustomDurationInDays();
        for (ActionRecord a : treatmentRecord.getActionRecords()) {
            if ((maxPriorityNumber == null || maxPriorityNumber < a.getCustomPriorityNumber())
                && a.getCustomPriorityNumber() != null) {
                maxPriorityNumber = a.getCustomPriorityNumber();
            }

            if () {
            durationSum += a.getCustomDurationInDays();
        }

        if (maxPriorityNumber == null) {
            actionRecord.setPriorityNumber(0);
        } else if (actionRecord.getPriorityNumber() > (maxPriorityNumber+1)) {
            throw new BadRequestException("Bad priority number");
        }

        if (durationSum > treatmentRecord.getDurationInDays()) {
            throw new BadRequestException("The duration of all the actionRecords exceed the duration of the treatmentRecord");
        }*/



        return actionRecordMapper.actionRecordToActionRecordResponse(
                actionRecordRepository.save(actionRecord)
        );    }

    @Override
    public void deleteActionRecord(Long actionRecordId, String token) {

        ActionRecord record = actionRecordRepository.findById(actionRecordId)
                .orElseThrow(() -> new ResourceNotFoundException("ActionRecord not found with id: " + actionRecordId));
        feignAnimalClient.hasPermissions(actionRecordId , false, false, true, false, token);


        actionRecordRepository.deleteById(actionRecordId);
    }
}
