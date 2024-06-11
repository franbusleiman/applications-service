package com.liro.treatments.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ActionRecordResponse {

    private Long id;
    private Long actionId;
    private Long treatmentRecordId;
    private Integer actualCycle;
    private Integer customDaysBetweenCycles;
    private Integer customDurationInDays;
    private Integer customNumberOfCycles;
    private Integer customPriorityNumber;
    private LocalDateTime date;

}
