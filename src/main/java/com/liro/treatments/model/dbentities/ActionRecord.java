package com.liro.treatments.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "actions_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"action", "treatmentRecord"})
public class ActionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer actualCycle;
    private Integer customDaysBetweenCycles;
    private Integer customDurationInDays;
    private Integer customNumberOfCycles;
    private Integer customPriorityNumber;
    private LocalDateTime date; // https://git.sauken.com.ar/pethc/backend-development/tasks/-/issues/38


//    @Enumerated(EnumType.STRING)
//    private ActionType actionType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "action_id")
    private Action action;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "treatment_record_id")
    private TreatmentRecord treatmentRecord;



}
