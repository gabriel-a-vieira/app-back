package com.softix.app_back.company.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, String> {

    Page<CompanyReview> findByCompanyIdOrderByCreatedAtDesc(String companyId, Pageable pageable);

    Optional<CompanyReview> findByIdAndUserId(String id, String userId);

    Optional<CompanyReview> findByCompanyIdAndUserId(String companyId, String userId);

    boolean existsByCompanyIdAndUserId(String companyId, String userId);

    @Query("""
                SELECT AVG(r.rating)
                FROM CompanyReview r
                WHERE r.companyId = :companyId
            """)
    Double findAverageByCompanyId(@Param("companyId") String companyId);

    long countByCompanyId(String companyId);

}