package com.softix.app_back.company.favorite;

import com.softix.app_back.company.CompanyRepository;
import com.softix.app_back.shared.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utils.security.SecurityUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyFavoriteService {

    private final CompanyFavoriteRepository companyFavoriteRepository;

    private final CompanyRepository companyRepository;

    @Transactional
    public boolean toggle(String companyId) {

        if (!companyRepository.existsById(companyId)) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Empresa nao encontrada");
        }

        String userId = SecurityUtils.userId();

        Optional<CompanyFavorite> existing = companyFavoriteRepository.findByCompanyIdAndUserId(companyId, userId);

        if (existing.isPresent()) {
            companyFavoriteRepository.delete(existing.get());
            return false;
        }

        CompanyFavorite favorite = new CompanyFavorite();
        favorite.setCompanyId(companyId);
        favorite.setUserId(userId);

        try {
            companyFavoriteRepository.saveAndFlush(favorite);
        } catch (DataIntegrityViolationException e) {
            /*
             * Corrida entre dois cliques quase simultaneos: outra
             * requisicao ja criou o mesmo favorito (company_id +
             * user_id) entre a checagem acima e este insert. Ja esta
             * favoritado, entao tratamos como sucesso.
             */
            return true;
        }

        return true;

    }

    @Transactional(readOnly = true)
    public Set<String> findFavoriteCompanyIds() {

        if (SecurityUtils.currentUser() == null) {
            return Set.of();
        }

        return companyFavoriteRepository.findByUserId(SecurityUtils.userId()).stream()
                .map(CompanyFavorite::getCompanyId)
                .collect(Collectors.toSet());

    }

    @Transactional(readOnly = true)
    public boolean isFavorite(String companyId) {

        if (SecurityUtils.currentUser() == null) {
            return false;
        }

        return companyFavoriteRepository.findByCompanyIdAndUserId(companyId, SecurityUtils.userId()).isPresent();

    }

}
