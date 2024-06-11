package com.liro.treatments.model.dbentities;

import com.liro.treatments.enums.ActionType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"actionRecords", "treatment"})
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer priorityNumber;
    private Integer daysBetweenCycles;
    private Integer numberOfCycles;
    private Integer durationInDays;
    private boolean permanent;
    private boolean cyclic;



    @Enumerated(EnumType.STRING)
    private ActionType actionType;


    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "action")
    private Set<ActionRecord> actionRecords;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "treatment_id")
    private Treatment treatment;

    // TODO: Add ActionsMedicineParts
    //  See if instead of medicines it's convenient to use the formulas, since a it can be 2 medicines with
    //  almost the same components from diferent brands

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "action")
    private ActionsMedicines actionsMedicines;


}
