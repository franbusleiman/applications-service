package com.liro.treatments.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "treatment_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"parentTreatmentRecord", "followupTreatments",
        "actionRecords", "treatment"})
public class TreatmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer daysBetweenFollowups;
    private String details;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate nextAppointment;
    private Integer priorityNumber;
    private Integer actualCycle;
    private Long animalId;
    private Long consultationId;
    private Long vetProfileId;
    private boolean valid;



//    @Enumerated(EnumType.STRING)
//    private TreatmentRecordType treatmentRecordType;


    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_treatment_record_id")
    private TreatmentRecord parentTreatmentRecord;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "parentTreatmentRecord")
    private Set<TreatmentRecord> followupTreatments;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "treatmentRecord")
    private Set<ActionRecord> actionRecords;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

}
