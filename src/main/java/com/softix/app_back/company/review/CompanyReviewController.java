package com.softix.app_back.company.review;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class CompanyReviewController {

    @Autowired
    CompanyReviewService companyReviewService;

    @Autowired
    CompanyReviewImageService companyReviewImageService;

    @GetMapping("/public/company/{companyId}/reviews")
    public Page<CompanyReviewDTO> findPublic(@PathVariable String companyId,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size) {
        return companyReviewService.findPublic(companyId, PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    @GetMapping("/public/company/{companyId}/review-summary")
    public CompanyReviewSummaryDTO summary(@PathVariable String companyId) {
        return companyReviewService.getSummary(companyId);
    }

    @GetMapping("/company-review/me")
    public CompanyReviewDTO findMine(@RequestParam String companyId) {
        return companyReviewService.findMine(companyId);
    }

    @PostMapping("/company-review")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyReviewDTO create(@Valid @RequestBody CompanyReviewDTO dto) {
        return companyReviewService.create(dto);
    }

    @PutMapping("/company-review/{id}")
    public CompanyReviewDTO update(@PathVariable String id,
                                   @Valid @RequestBody CompanyReviewDTO dto) {
        return companyReviewService.update(id, dto);
    }

    @DeleteMapping("/company-review/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        companyReviewService.delete(id);
    }

    @PostMapping("/company-review/image")
    public Map<String, String> uploadImage(@RequestParam("file") MultipartFile file) {
        return Map.of("url", companyReviewImageService.save(file));
    }

}
