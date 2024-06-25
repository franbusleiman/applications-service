package com.liro.applications.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "application_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String details;
    private LocalDate applicationDate;
    private LocalDate endDate;
    private Long animalId;
    private Long vetProfileId;
    private boolean valid;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "applicationRecord")
    private ApplicationMedicines applicationMedicines;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "application_type_id")
    private ApplicationType applicationType;
}
