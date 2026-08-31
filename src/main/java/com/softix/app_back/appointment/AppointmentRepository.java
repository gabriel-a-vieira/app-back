package com.softix.app_back.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {

    List<Appointment> findByIdIn(Collection<String> ids);

    List<Appointment> findByIdInAndCompanyId(Collection<String> ids, String companyId);

    @Query("""
            SELECT a
            FROM Appointment a
            LEFT JOIN a.client client
            LEFT JOIN client.person clientPerson
            LEFT JOIN a.professional professional
            LEFT JOIN professional.person professionalPerson
            WHERE (:companyId IS NULL OR a.companyId = :companyId)
            AND (:status IS NULL OR a.status = :status)
            AND (:clientId IS NULL OR :clientId = '' OR client.id = :clientId)
            AND (:professionalId IS NULL OR :professionalId = '' OR professional.id = :professionalId)
            AND (a.startAt >= :dateFrom)
            AND (a.startAt < :dateTo)
            AND (:search IS NULL OR :search = '' OR LOWER(clientPerson.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(professionalPerson.name) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<Appointment> findAdvanced(@Param("companyId") String companyId,
                                   @Param("search") String search,
                                   @Param("status") AppointmentStatus status,
                                   @Param("clientId") String clientId,
                                   @Param("professionalId") String professionalId,
                                   @Param("dateFrom") LocalDateTime dateFrom,
                                   @Param("dateTo") LocalDateTime dateTo,
                                   Pageable pageable);

    @Query("""
            SELECT CASE
                WHEN COUNT(a) > 0
                THEN true
                ELSE false
            END
            FROM Appointment a
            WHERE a.companyId = :companyId 
            AND a.professionalId = :professionalId 
            AND a.status IN :statuses 
            AND (:ignoreId IS NULL OR a.id <> :ignoreId)
            AND a.startAt < :endAt
            AND a.endAt > :startAt
            """)
    boolean existsConflict(@Param("companyId") String companyId,
                           @Param("professionalId") String professionalId,
                           @Param("statuses") List<AppointmentStatus> statuses,
                           @Param("startAt") LocalDateTime startAt,
                           @Param("endAt") LocalDateTime endAt,
                           @Param("ignoreId") String ignoreId);

    List<Appointment> findByCompanyIdAndProfessionalIdAndStatusInAndStartAtLessThanAndEndAtGreaterThan(String companyId,
                                                                                                       String professionalId,
                                                                                                       List<AppointmentStatus> statuses,
                                                                                                       LocalDateTime endAt,
                                                                                                       LocalDateTime startAt);

    @Query("""
            SELECT a
            FROM Appointment a
            JOIN a.client c
            WHERE
                c.userId = :userId
                AND a.startAt >= :dateFrom
            ORDER BY a.startAt ASC
            """)
    Page<Appointment> findMine(@Param("userId") String userId,
                               @Param("dateFrom") LocalDateTime dateFrom,
                               Pageable pageable);


    @Query("""
            SELECT a
            FROM Appointment a
            JOIN a.client c
            WHERE
                a.id = :id
                AND c.userId = :userId
            """)
    Optional<Appointment> findMineById(@Param("id") String id,
                                       @Param("userId") String userId);

}