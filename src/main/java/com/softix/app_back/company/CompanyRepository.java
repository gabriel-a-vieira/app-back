package com.softix.app_back.company;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("""
            SELECT c
              FROM Company c
             WHERE c.status = com.softix.app_back.company.CompanyStatus.ACTIVE
               AND (:type IS NULL OR c.type = :type)
               AND (
                    :search IS NULL
                    OR :search = ''
                    OR LOWER(c.tradeName) LIKE CONCAT('%', LOWER(CAST(:search AS string)), '%')
                    OR LOWER(c.legalName) LIKE CONCAT('%', LOWER(CAST(:search AS string)), '%')
               )
            """)
    Page<Company> findPublicCompanies(
            @Param("type") CompanyType type,
            @Param("search") String search,
            Pageable pageable
    );

}