package com.softix.app_back.product.public_api;

import com.softix.app_back.product.ProductDTO;
import com.softix.app_back.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/company")
public class PublicProductController {

    @Autowired
    ProductService productService;


    @GetMapping("/{companyId}/products")
    public List<ProductDTO> findProducts(@PathVariable String companyId) {
        return productService.findPublicProducts(companyId);
    }

}
