package com.softix.app_back.company.review;

import com.softix.app_back.company.CompanyRepository;
import com.softix.app_back.config.JWTUserData;
import com.softix.app_back.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

@Service
public class CompanyReviewService {

    @Autowired
    CompanyReviewRepository companyReviewRepository;

    @Autowired
    CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public Page<CompanyReviewDTO> findPublic(String companyId, Pageable pageable) {
        return companyReviewRepository.findByCompanyIdOrderByCreatedAtDesc(companyId, pageable).map(CompanyReviewDTO::new);
    }

    @Transactional(readOnly = true)
    public CompanyReviewSummaryDTO getSummary(String companyId) {

        Double average = companyReviewRepository.findAverageByCompanyId(companyId);
        long total = companyReviewRepository.countByCompanyId(companyId);

        return new CompanyReviewSummaryDTO(average != null ? average : 0.0, total);

    }

    @Transactional(readOnly = true)
    public CompanyReviewDTO findMine(String companyId) {
        return companyReviewRepository.findByCompanyIdAndUserId(companyId, SecurityUtils.userId()).map(CompanyReviewDTO::new).orElse(null);
    }

    @Transactional
    public CompanyReviewDTO create(CompanyReviewDTO dto) {

        String userId = SecurityUtils.userId();
        validateCompany(dto.getCompanyId());

        if (companyReviewRepository.existsByCompanyIdAndUserId(dto.getCompanyId(), userId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Voce ja avaliou este estabelecimento");
        }

        CompanyReview review = new CompanyReview();

        review.setCompanyId(dto.getCompanyId());
        review.setUserId(userId);
        review.setRating(dto.getRating());
        review.setComment(normalize(dto.getComment()));
        review.setImageUrl(normalize(dto.getImageUrl()));

        companyReviewRepository.save(review);

        return new CompanyReviewDTO(review);

    }

    @Transactional
    public CompanyReviewDTO update(String id, CompanyReviewDTO dto) {

        CompanyReview review = companyReviewRepository.findByIdAndUserId(id, SecurityUtils.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliacao nao encontrada"));

        review.setRating(dto.getRating());
        review.setComment(normalize(dto.getComment()));
        review.setImageUrl(normalize(dto.getImageUrl()));

        companyReviewRepository.save(review);

        return new CompanyReviewDTO(review);

    }

    @Transactional
    public void delete(String id) {

        CompanyReview review = companyReviewRepository.findByIdAndUserId(id, SecurityUtils.userId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avaliacao nao encontrada"));
        companyReviewRepository.delete(review);

    }

    private void validateCompany(String companyId) {

        if (!companyRepository.existsById(companyId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa nao encontrada");
        }

    }

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        String normalized = value.trim();

        return normalized.isEmpty() ? null : normalized;

    }

}
