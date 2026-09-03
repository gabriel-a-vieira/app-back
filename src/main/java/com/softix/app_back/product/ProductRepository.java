package com.softix.app_back.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, String> {

    @Query("""
            SELECT p
              FROM Product p
              LEFT JOIN FETCH p.company company
             WHERE (:companyId IS NULL OR p.companyId = :companyId)
               AND (:status IS NULL OR p.status = :status)
               AND (:minPrice IS NULL OR p.price >= :minPrice)
               AND (:maxPrice IS NULL OR p.price <= :maxPrice)
               AND (
                    :search IS NULL
                    OR :search = ''
                    OR LOWER(p.name)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(p.description)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(company.tradeName)
                        LIKE LOWER(CONCAT('%', :search, '%'))
                    OR LOWER(company.legalName)
                        LIKE LOWER(CONCAT('%', :search, '%'))
               )
            """)
    Page<Product> findAdvanced(@Param("companyId") String companyId,
                               @Param("search") String search,
                               @Param("status") ProductStatus status,
                               @Param("minPrice") BigDecimal minPrice,
                               @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);


    @Query("""
            SELECT p
              FROM Product p
              LEFT JOIN FETCH p.company company
             WHERE p.id = :id
               AND (:companyId IS NULL OR p.companyId = :companyId)
            """)
    Optional<Product> findScopedById(@Param("id") String id,
                                     @Param("companyId") String companyId);


    @Query("""
            SELECT p
              FROM Product p
             WHERE p.id IN :ids
               AND (:companyId IS NULL OR p.companyId = :companyId)
            """)
    List<Product> findScopedByIds(@Param("ids") Collection<String> ids,
                                  @Param("companyId") String companyId);


    @Query("""
            SELECT p
              FROM Product p
             WHERE p.companyId = :companyId
               AND p.status = com.softix.app_back.product.ProductStatus.ACTIVE
             ORDER BY p.name ASC
            """)
    List<Product> findPublicProducts(@Param("companyId") String companyId);

}