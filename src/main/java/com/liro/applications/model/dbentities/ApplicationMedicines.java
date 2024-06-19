package com.liro.applications.model.dbentities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "application_medicines",
        indexes = @Index(name = "medicine_index",  columnList="medicineId"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationMedicines {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long medicineId;
    private Long quantity;
    private String quantityType;
    private String details;
    private String lote;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST)
    @JoinColumn(name = "application_record_id")
    private ApplicationRecord applicationRecord;
}
