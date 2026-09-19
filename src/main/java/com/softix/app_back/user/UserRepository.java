package com.softix.app_back.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<UserDetails> findUserByEmail(String email);

    UserDetails findByEmail(String email);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findByEmailIgnoreCase(String email);

    Optional<User> findByIdAndCompanyId(String id, String companyId);

    @Query("""
            SELECT u
            FROM User u
            WHERE (:search IS NULL OR :search = ''
                OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))
            AND (:role IS NULL OR u.role = :role)
            """)
    Page<User> findAdvanced(
            @Param("search") String search,
            @Param("role") UserRole role,
            Pageable pageable
    );

    @Query("""
            SELECT u
            FROM User u
            WHERE u.id IN :ids
            AND (:search IS NULL OR :search = ''
                OR LOWER(u.name) LIKE LOWER(CONCAT('%', :search, '%'))
                OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))
            """)
    Page<User> findByIdInAndSearch(
            @Param("ids") List<String> ids,
            @Param("search") String search,
            Pageable pageable
    );

}
