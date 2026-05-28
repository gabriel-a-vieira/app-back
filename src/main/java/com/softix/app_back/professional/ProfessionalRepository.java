package com.softix.app_back.professional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query("""
    SELECT p
    FROM Professional p
    LEFT JOIN p.person person
    LEFT JOIN person.address.city city
    LEFT JOIN city.state state
    WHERE
        (:status IS NULL OR p.status = :status)
        AND (
            :search IS NULL OR :search = ''
            OR LOWER(person.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR person.cpfCnpj LIKE CONCAT('%', :search, '%')
            OR person.phone LIKE CONCAT('%', :search, '%')
            OR LOWER(city.name) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(state.abbreviation) LIKE LOWER(CONCAT('%', :search, '%'))
        )
        AND (:name IS NULL OR :name = '' OR LOWER(person.name) LIKE LOWER(CONCAT('%', :name, '%')))
        AND (:cpfCnpj IS NULL OR :cpfCnpj = '' OR person.cpfCnpj LIKE CONCAT('%', :cpfCnpj, '%'))
        AND (:phone IS NULL OR :phone = '' OR person.phone LIKE CONCAT('%', :phone, '%'))
        AND (:city IS NULL OR :city = '' OR LOWER(city.name) LIKE LOWER(CONCAT('%', :city, '%')))
        AND (:state IS NULL OR :state = '' OR LOWER(state.abbreviation) = LOWER(:state))
    """)
    Page<Professional> findAdvanced(
            @Param("search") String search,
            @Param("name") String name,
            @Param("cpfCnpj") String cpfCnpj,
            @Param("phone") String phone,
            @Param("city") String city,
            @Param("state") String state,
            @Param("status") ProfessionalStatus status,
            Pageable pageable
    );

}
