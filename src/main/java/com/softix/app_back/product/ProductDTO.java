package com.softix.app_back.product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {

    private String id;
    private String companyId;
    private String companyName;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private String imageUrl;
    private ProductStatus status;

    public ProductDTO() {}

    public ProductDTO(Product product) {

        this.id = product.getId();
        this.companyId = product.getCompanyId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.imageUrl = product.getImageUrl();
        this.status = product.getStatus();

        if (product.getCompany() != null) {

            if (product.getCompany().getTradeName() != null && !product.getCompany().getTradeName().isBlank()) {
                this.companyName = product.getCompany().getTradeName();
            } else {
                this.companyName = product.getCompany().getLegalName();
            }

        }

    }

}