package com.liro.treatments.repositories;

import com.liro.treatments.model.dbentities.ActionRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionRecordRepository extends JpaRepository<ActionRecord, Long> {

    Page<ActionRecord> findAllByTreatmentRecordId(Long treatmentRecordId, Pageable pageable);


}
