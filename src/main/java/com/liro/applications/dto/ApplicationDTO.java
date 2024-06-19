package com.liro.applications.dto;

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
public class ApplicationDTO {

    private String name;
    private String formalName;
    private Integer durationInDays;
    private Integer dayBetweenCycles;
    private Long applicationTypeId;
    private Boolean cyclic;
    private boolean permanent;
    private String details;

}
