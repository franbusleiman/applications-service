package com.liro.treatments.repositories;

import com.liro.treatments.model.dbentities.Action;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    Page<Action> findAllByTreatmentId(Long treatmentId, Pageable pageable);

}
