package com.liro.treatments.repositories;

import com.liro.treatments.model.dbentities.Treatment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    Page<Treatment> findAllByNameContaining(String nameContaining, Pageable pageable);

}
