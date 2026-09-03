package com.softix.app_back.product;

import com.softix.app_back.company.Company;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import utils.model.tenant.TenantEntity;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "product")
public class Product extends TenantEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "name", length = 150, nullable = false)
    private String name;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "price", precision = 12, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "image_url", length = 1000, nullable = false)
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private ProductStatus status = ProductStatus.ACTIVE;

}