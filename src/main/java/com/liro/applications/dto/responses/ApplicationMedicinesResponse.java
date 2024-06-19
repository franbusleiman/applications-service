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
public class ApplicationMedicinesResponse {
    private Long id;
    private Long medicineId;
    private Long quantity;
    private String quantityType;
    private String details;
    private String lote;
}
