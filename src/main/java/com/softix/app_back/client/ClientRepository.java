package com.softix.app_back.client;

import com.softix.app_back.payment.PaymentMethod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ClientRepository extends JpaRepository<Client, String> {

    Client findClientByPersonName(String personName);

    List<Client> findByIdIn(Collection<String> ids);

    boolean existsByCompanyIdAndPerson_CpfCnpj(String companyId, String cpfCnpj);

    boolean existsByCompanyIdAndPerson_CpfCnpjAndIdNot(
            String companyId,
            String cpfCnpj,
            String id
    );

    @Query("""
        SELECT c
        FROM Client c
        LEFT JOIN c.person person
        LEFT JOIN person.address.city city
        LEFT JOIN city.state state
        WHERE
            (:companyId IS NULL OR c.companyId = :companyId)
            AND (:status IS NULL OR c.status = :status)
            AND (:preferredPaymentMethod IS NULL OR c.preferredPaymentMethod = :preferredPaymentMethod)
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
    Page<Client> findAdvanced(
            @Param("companyId") String companyId,
            @Param("search") String search,
            @Param("name") String name,
            @Param("cpfCnpj") String cpfCnpj,
            @Param("phone") String phone,
            @Param("city") String city,
            @Param("state") String state,
            @Param("status") ClientStatus status,
            @Param("preferredPaymentMethod") PaymentMethod preferredPaymentMethod,
            Pageable pageable
    );
}