package com.liro.applications.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.liro.applications.dto.ApplicationMedicinesDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
public class ApplicationRecordResponse {

    private Long id;
    private String details;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate applicationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private ApplicationMedicinesResponse applicationMedicines;
    private Long animalId;
    private Long applicationId;
}
