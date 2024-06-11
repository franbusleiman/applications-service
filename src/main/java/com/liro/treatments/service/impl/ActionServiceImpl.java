package com.liro.treatments.service.impl;

import com.liro.treatments.config.FeignAnimalClient;
import com.liro.treatments.dto.ActionDTO;
import com.liro.treatments.dto.mappers.ActionMapper;
import com.liro.treatments.dto.responses.ActionResponse;
import com.liro.treatments.exceptions.ResourceNotFoundException;
import com.liro.treatments.model.dbentities.Action;
import com.liro.treatments.model.dbentities.Treatment;
import com.liro.treatments.repositories.ActionRepository;
import com.liro.treatments.repositories.TreatmentRepository;
import com.liro.treatments.service.ActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;
    private final TreatmentRepository treatmentRepository;
    private final ActionMapper actionMapper;

    @Autowired
    public ActionServiceImpl(ActionRepository actionRepository,
                             TreatmentRepository treatmentRepository,
                             ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.treatmentRepository = treatmentRepository;
        this.actionMapper = actionMapper;
    }

    @Override
    public Page<ActionResponse> findAllByTreatmentId(Long treatmentId, Pageable pageable) {
        return actionRepository.findAllByTreatmentId(treatmentId, pageable)
                .map(actionMapper::actionToActionResponse);
    }

    @Override
    public ActionResponse findById(Long actionId) {
        Action action = actionRepository.findById(actionId)
                .orElseThrow(() -> new ResourceNotFoundException("Action not found with id: " + actionId));

        return actionMapper.actionToActionResponse(action);
    }

    @Override
    public ActionResponse createAction(ActionDTO actionDTO) {
//        UserResponse user = userService.getUser(token);
//
//        // TODO: Change to call the vet validation endpoint to be created in the userService
//        if (user.getVetProfileId() == null) {
//            throw new UnauthorizedException("You are not a valid veterinary");
//        }

        Treatment treatment = treatmentRepository.findById(actionDTO.getTreatmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Treatment not found with id: " + actionDTO.getTreatmentId()));

        Action action = actionMapper.actionDtoToAction(actionDTO);
        action.setTreatment(treatment);
        if (treatment.getActions() == null) treatment.setActions(new HashSet<>());
        treatment.getActions().add(action);


        Integer maxPriorityNumber = null;
        int durationSum = action.getDurationInDays();
        for (Action a : treatment.getActions()) {
            if (maxPriorityNumber == null || maxPriorityNumber < a.getPriorityNumber()) {
                maxPriorityNumber = a.getPriorityNumber();
            }

            durationSum += a.getDurationInDays();
        }

        if (maxPriorityNumber == null) {
            action.setPriorityNumber(0);
        } else if (action.getPriorityNumber() > (maxPriorityNumber+1)) {
            throw new BadRequestException("Bad priority number");
        }

        if (durationSum > treatment.getDurationInDays()) {
            throw new BadRequestException("The duration of all the actions exceed the duration of the treatment");
        }

        return actionMapper.actionToActionResponse(
                actionRepository.save(action)
        );    }

    @Override
    public void deleteAction(Long actionId) {

        actionRepository.deleteById(actionId);
    }
}
