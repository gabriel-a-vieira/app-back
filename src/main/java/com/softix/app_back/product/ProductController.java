package com.softix.app_back.product;

import lombok.RequiredArgsConstructor;
import com.softix.app_back.permission.CrudAction;
import com.softix.app_back.permission.RequiresPermission;
import com.softix.app_back.permission.SystemModule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @RequiresPermission(module = SystemModule.PRODUCT, action = CrudAction.LIST)
    @GetMapping
    public Page<ProductDTO> findAll(@RequestParam(required = false) String search,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) BigDecimal minPrice,
                                    @RequestParam(required = false) BigDecimal maxPrice,
                                    @RequestParam(required = false) String companyId,
                                    @RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        return productService.findAll(search, status, minPrice, maxPrice, companyId, pageable);

    }


    @GetMapping("/statuses")
    public List<String> findStatuses() {
        return productService.findStatuses();
    }


    @RequiresPermission(module = SystemModule.PRODUCT, action = CrudAction.LIST)
    @GetMapping("/{id}")
    public ProductDTO findById(@PathVariable String id,
                               @RequestParam(required = false) String companyId) {
        return productService.findById(id, companyId);
    }


    @RequiresPermission(module = SystemModule.PRODUCT, action = CrudAction.CREATE)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO create(@RequestBody ProductDTO dto) {
        return productService.save(dto);
    }


    @RequiresPermission(module = SystemModule.PRODUCT, action = CrudAction.UPDATE)
    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable String id,
                             @RequestBody ProductDTO dto) {
        return productService.update(id, dto);

    }


    @RequiresPermission(module = SystemModule.PRODUCT, action = CrudAction.DELETE)
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestBody List<String> ids,
                           @RequestParam(required = false) String companyId) {
        productService.deleteMany(ids, companyId);
    }

}