package com.liro.applications.repositories;

import com.liro.applications.model.dbentities.ApplicationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationTypeRepository extends JpaRepository<ApplicationType, Long> {

    Page<ApplicationType> findAllByTypeNameContaining(String nameContaining, Pageable pageable);

}
