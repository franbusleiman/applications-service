package com.liro.treatments.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "treatments",
        indexes = @Index(name = "by_age_index",  columnList="byAge"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"treatmentType", "treatmentRecords"})
public class Treatment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String formalName;
    private Integer durationInDays;
    private Integer dayBetweenCycles;
    private Boolean cyclic;
    private boolean permanent;
    private String details;



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "treatment_type_id")
    private TreatmentType treatmentType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "treatment")
    private Set<TreatmentRecord> treatmentRecords;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "treatment")
    private Set<Action> actions;


}
