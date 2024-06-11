package com.liro.treatments.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "treatment_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "treatments")
public class TreatmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;
    private String details;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "treatmentType")
    private Set<Treatment> treatments;


}
