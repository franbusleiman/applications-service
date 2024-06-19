package com.liro.applications.repositories;

import com.liro.applications.model.dbentities.ApplicationRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {

    Page<ApplicationRecord> findAllByAnimalId(Long animalId, Pageable pageable);

    Page<ApplicationRecord> findAllByAnimalIdAndApplicationId(Long animalId, Long applicationId, Pageable pageable);

    Page<ApplicationRecord> findAllByAnimalIdAndApplicationApplicationTypeId(Long animalId, Long applicationTypeId,
                                                                       Pageable pageable);

   @Query(value = "SELECT * FROM application_records WHERE animal_id = :animalId " +
           "AND application_id = :applicationId ORDER BY application_date DESC LIMIT 1",
           nativeQuery = true)
   Optional<ApplicationRecord> findLastByAnimalIdAndApplicationId(@Param("animalId") Long animalId,
                                                              @Param("applicationId") Long applicationId);

    @Query(value = "SELECT * from application_records WHERE animal_id = :animalId AND application_id IN " +
            "(SELECT id FROM applications WHERE application_type_id = :applicationTypeId) " +
            "ORDER BY application_date DESC LIMIT 1",
            nativeQuery = true)
    Optional<ApplicationRecord> findLastByAnimalIdAndApplicationTypeId(@Param("animalId") Long animalId,
                                                                   @Param("applicationTypeId") Long applicationTypeId);

}
