package com.liro.treatments.repositories;

import com.liro.treatments.model.dbentities.TreatmentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentTypeRepository extends JpaRepository<TreatmentType, Long> {

    Page<TreatmentType> findAllByTypeNameContaining(String nameContaining, Pageable pageable);

}
