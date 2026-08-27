package com.softix.app_back.service_offering;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service-offering")
public class ServiceOfferingController {

    @Autowired
    ServiceOfferingService serviceOfferingService;

    @GetMapping
    public Page<ServiceOfferingDTO> findAll(@RequestParam(required = false) String search,
                                            @RequestParam(required = false) String status,
                                            @RequestParam(required = false) Integer minDuration,
                                            @RequestParam(required = false) Integer maxDuration,
                                            @RequestParam(required = false) Double minPrice,
                                            @RequestParam(required = false) Double maxPrice,
                                            @RequestParam(required = false) String companyId,
                                            @RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));

        return serviceOfferingService.findAll(search, status, minDuration, maxDuration, minPrice, maxPrice, companyId, pageable);

    }

    @GetMapping("/{id}")
    public ServiceOfferingDTO findById(@PathVariable String id) {
        return serviceOfferingService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceOfferingDTO create(@Valid @RequestBody ServiceOfferingDTO dto) {
        return serviceOfferingService.save(dto);
    }

    @PutMapping("/{id}")
    public ServiceOfferingDTO update(@PathVariable String id, @Valid @RequestBody ServiceOfferingDTO dto) {
        return serviceOfferingService.update(id, dto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMany(@RequestBody List<String> ids) {
        serviceOfferingService.deleteMany(ids);
    }

}