package com.liro.treatments.dto;

import com.liro.treatments.enums.ActionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ActionDTO {

    private Long treatmentId;
    private Integer priorityNumber;
    private Integer daysBetweenCycles;
    private Integer numberOfCycles;
    private Integer durationInDays;
    private Boolean permanent;
    private Boolean cyclic;
    private ActionType actionType;




}
