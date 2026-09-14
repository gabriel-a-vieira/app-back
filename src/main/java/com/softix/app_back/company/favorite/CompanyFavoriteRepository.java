package com.softix.app_back.company.favorite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompanyFavoriteRepository extends JpaRepository<CompanyFavorite, String> {

    Optional<CompanyFavorite> findByCompanyIdAndUserId(String companyId, String userId);

    List<CompanyFavorite> findByUserId(String userId);

}
