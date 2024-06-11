package com.liro.treatments.model.dbentities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "actions_medicines",
        indexes = @Index(name = "medicine_index",  columnList="medicineId"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "action")
public class ActionsMedicines{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "action_id")
    private Action action;

    private Long medicineId;

    private Long quantity;
    private String quantityType;

    /**
     * Quantity per pet weight or extra details
     */
    private String details;
}
