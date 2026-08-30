package com.softix.app_back.company.review;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class CompanyReviewDTO {

    private String id;

    @NotBlank(message = "Empresa e obrigatoria")
    private String companyId;

    @NotNull(message = "Nota e obrigatoria")
    @Min(value = 1, message = "Nota minima e 1")
    @Max(value = 5, message = "Nota maxima e 5")
    private Integer rating;

    private String comment;

    private String imageUrl;

    private String authorName;

    private Date createdAt;

    public CompanyReviewDTO() {
    }

    public CompanyReviewDTO(CompanyReview review) {

        this.id = review.getId();
        this.companyId = review.getCompanyId();
        this.rating = review.getRating();
        this.comment = review.getComment();
        this.imageUrl = review.getImageUrl();

        if (review.getUser() != null) {

            String name = review.getUser().getName();

            if (name != null && !name.isBlank()) {
                this.authorName = name.trim().split("\\s+")[0];
            }

        }

        this.createdAt = review.getCreatedAt();

    }

}
