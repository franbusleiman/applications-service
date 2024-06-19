package com.liro.applications.model.dbentities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "application_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = "applications")
public class ApplicationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String typeName;
    private String details;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "applicationType")
    private Set<Application> applications;


}
