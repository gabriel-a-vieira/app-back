package com.softix.app_back.professional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfessionalRepository extends JpaRepository<Professional, String> {

    Page<Professional> findByStatus(
            ProfessionalStatus status,
            Pageable pageable
    );

    Page<Professional> findByStatusAndPersonNameContainingIgnoreCase(
            ProfessionalStatus status,
            String name,
            Pageable pageable
    );

    List<Professional> findByIdIn(List<String> ids);

}
