package com.liro.treatments.repositories;

import com.liro.treatments.model.dbentities.TreatmentRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TreatmentRecordRepository extends JpaRepository<TreatmentRecord, Long> {

    Page<TreatmentRecord> findAllByAnimalId(Long animalId, Pageable pageable);

    Page<TreatmentRecord> findAllByAnimalIdAndTreatmentId(Long animalId, Long treatmentId, Pageable pageable);

    Page<TreatmentRecord> findAllByAnimalIdAndTreatmentTreatmentTypeId(Long animalId, Long treatmentTypeId,
                                                                       Pageable pageable);

   @Query(value = "SELECT * FROM treatment_records WHERE animal_id = :animalId " +
           "AND treatment_id = :treatmentId ORDER BY start_date DESC LIMIT 1",
           nativeQuery = true)
   Optional<TreatmentRecord> findLastByAnimalIdAndTreatmentId(@Param("animalId") Long animalId,
                                                              @Param("treatmentId") Long treatmentId);

    @Query(value = "SELECT * from treatment_records WHERE animal_id = :animalId AND treatment_id IN " +
            "(SELECT id FROM treatments WHERE treatment_type_id = :treatmentTypeId) " +
            "ORDER BY start_date DESC LIMIT 1",
            nativeQuery = true)
    Optional<TreatmentRecord> findLastByAnimalIdAndTreatmentTypeId(@Param("animalId") Long animalId,
                                                                   @Param("treatmentTypeId") Long treatmentTypeId);

}
