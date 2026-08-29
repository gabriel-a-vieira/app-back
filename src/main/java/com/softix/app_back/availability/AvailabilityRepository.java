package com.softix.app_back.availability;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, String> {

    List<Availability> findByProfessionalIdAndDayWeek(String professionalId, DayOfWeek dayWeek);

    List<Availability> findByIdIn(Collection<String> ids);

    @Query("""
            SELECT a
            FROM Availability a
            LEFT JOIN a.professional professional
            LEFT JOIN professional.person person
            WHERE (:companyId IS NULL OR a.companyId = :companyId)
            AND (:professionalId IS NULL OR :professionalId = '' OR a.professionalId = :professionalId)
            AND (:dayWeek IS NULL OR a.dayWeek = :dayWeek)
            AND (
                :search IS NULL OR :search = '' 
                OR LOWER(person.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR person.cpfCnpj LIKE CONCAT('%', :search, '%')
            )
            """)
    Page<Availability> findAdvanced(@Param("companyId") String companyId,
                                    @Param("professionalId") String professionalId,
                                    @Param("dayWeek") DayOfWeek dayWeek,
                                    @Param("search") String search,
                                    Pageable pageable);

    @Query("""
           SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
           FROM Availability a
           WHERE a.companyId = :companyId
           AND a.professionalId = :professionalId
           AND a.dayWeek = :dayWeek
           AND (:ignoreId IS NULL OR a.id <> :ignoreId)
           AND a.startTime < :endTime
           AND a.endTime > :startTime
           """)
    boolean existsOverlappingAvailability(@Param("companyId") String companyId,
                                          @Param("professionalId") String professionalId,
                                          @Param("dayWeek") DayOfWeek dayWeek,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime,
                                          @Param("ignoreId") String ignoreId);

    List<Availability> findByProfessionalIdAndDayWeekAndCompanyIdOrderByStartTimeAsc(String professionalId, DayOfWeek dayWeek, String companyId);

}