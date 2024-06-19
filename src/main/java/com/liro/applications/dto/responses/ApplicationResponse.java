package com.liro.applications.dto.responses;

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
public class ApplicationResponse {

    private Long id;
    private String name;
    private String formalName;
    private Integer durationInDays;
    private Integer dayBetweenCycles;
    private Boolean cyclic;
    private boolean permanent;
    private String details;
    private Long applicationTypeId;

}
