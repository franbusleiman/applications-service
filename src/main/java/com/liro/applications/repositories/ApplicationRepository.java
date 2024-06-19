package com.liro.applications.repositories;

import com.liro.applications.model.dbentities.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Page<Application> findAllByNameContaining(String nameContaining, Pageable pageable);

}
