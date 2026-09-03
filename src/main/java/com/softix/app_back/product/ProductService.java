package com.softix.app_back.product;

import com.softix.app_back.company.CompanyRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CompanyRepository companyRepository;


    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(String search,
                                    String status,
                                    BigDecimal minPrice,
                                    BigDecimal maxPrice,
                                    String companyId,
                                    Pageable pageable) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);
        ProductStatus parsedStatus = parseStatus(status);
        String normalizedSearch = StringUtils.trimToNull(search);

        return productRepository.findAdvanced(resolvedCompanyId, normalizedSearch, parsedStatus, minPrice, maxPrice, pageable).map(ProductDTO::new);

    }


    @Transactional(readOnly = true)
    public ProductDTO findById(String id,
                               String companyId) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);
        Product product = productRepository.findScopedById(id, resolvedCompanyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        return new ProductDTO(product);

    }


    @Transactional
    public ProductDTO save(ProductDTO dto) {

        validate(dto);
        String resolvedCompanyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());

        if (resolvedCompanyId == null || resolvedCompanyId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa obrigatoria");
        }

        if (!companyRepository.existsById(resolvedCompanyId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa nao encontrada");
        }

        Product product = new Product();
        product.setCompanyId(resolvedCompanyId);
        applyDTO(product, dto);
        product.setStatus(dto.getStatus() != null ? dto.getStatus() : ProductStatus.ACTIVE);

        productRepository.save(product);
        return new ProductDTO(product);

    }


    @Transactional
    public ProductDTO update(String id,
                             ProductDTO dto) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());

        Product product = productRepository.findScopedById(id, resolvedCompanyId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto nao encontrado"));

        validate(dto);
        applyDTO(product, dto);

        if (dto.getStatus() != null) {
            product.setStatus(dto.getStatus());
        }

        productRepository.save(product);
        return new ProductDTO(product);

    }


    @Transactional
    public void deleteMany(List<String> ids,
                           String companyId) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum produto informado");
        }

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);
        List<Product> products = productRepository.findScopedByIds(ids, resolvedCompanyId);

        for (Product product : products) {
            product.setStatus(ProductStatus.INACTIVE);
        }

        productRepository.saveAll(products);

    }


    @Transactional(readOnly = true)
    public List<ProductDTO> findPublicProducts(String companyId) {

        if (companyId == null || companyId.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Empresa nao informada");
        }

        return productRepository.findPublicProducts(companyId).stream().map(ProductDTO::new).toList();

    }


    public List<String> findStatuses() {
        return Arrays.stream(ProductStatus.values()).map(Enum::name).toList();
    }


    private void applyDTO(Product product,
                          ProductDTO dto) {

        product.setName(StringUtils.trimToNull(dto.getName()));
        product.setDescription(StringUtils.trimToNull(dto.getDescription()));
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setImageUrl(StringUtils.trimToNull(dto.getImageUrl()));

    }


    private void validate(ProductDTO dto) {

        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome do produto obrigatorio");
        }

        if (dto.getPrice() == null || dto.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Preco invalido");
        }

        if (dto.getStockQuantity() == null || dto.getStockQuantity() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade em estoque invalida");
        }

        if (dto.getImageUrl() == null || dto.getImageUrl().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Imagem do produto obrigatoria");
        }

    }


    private ProductStatus parseStatus(String status) {

        if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status)) {
            return null;
        }

        try {
            return ProductStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status invalido");
        }

    }

}