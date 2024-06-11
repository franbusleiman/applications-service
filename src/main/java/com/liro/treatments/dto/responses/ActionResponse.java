package com.liro.treatments.dto.responses;

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
public class ActionResponse {

    private Long id;
    private Long treatmentId;
    private Integer priorityNumber;
    private Integer daysBetweenCycles;
    private Integer numberOfCycles;
    private Integer durationInDays;
    private Boolean permanent;
    private Boolean cyclic;
    private ActionType actionType;

}
