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
public class ApplicationMedicationDTO {
    private Long medicineId;
    private Long quantity;
    private String quantityType;
    private String details;
    private String lote;
}
