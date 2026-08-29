package com.softix.app_back.service_offering;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ServiceOfferingRepository
        extends JpaRepository<ServiceOffering, String> {

    List<ServiceOffering> findByIdIn(Collection<String> ids);

    boolean existsByCompanyIdAndNameIgnoreCase(
            String companyId,
            String name
    );

    boolean existsByCompanyIdAndNameIgnoreCaseAndIdNot(
            String companyId,
            String name,
            String id
    );

    @Query("""
        SELECT s
        FROM ServiceOffering s
        WHERE
            (:companyId IS NULL OR s.companyId = :companyId)

            AND (
                :status IS NULL
                OR s.status = :status
            )

            AND (
                :search IS NULL
                OR :search = ''
                OR LOWER(s.name)
                    LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(COALESCE(s.description, ''))
                    LIKE LOWER(CONCAT('%', :search, '%'))
            )

            AND (
                :minDuration IS NULL
                OR s.durationMinutes >= :minDuration
            )

            AND (
                :maxDuration IS NULL
                OR s.durationMinutes <= :maxDuration
            )

            AND (
                :minPrice IS NULL
                OR s.price >= :minPrice
            )

            AND (
                :maxPrice IS NULL
                OR s.price <= :maxPrice
            )
        """)
    Page<ServiceOffering> findAdvanced(
            @Param("companyId") String companyId,
            @Param("search") String search,
            @Param("status") ServiceOfferingStatus status,
            @Param("minDuration") Integer minDuration,
            @Param("maxDuration") Integer maxDuration,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );

    List<ServiceOffering> findByIdInAndCompanyId(
            Collection<String> ids,
            String companyId
    );

}