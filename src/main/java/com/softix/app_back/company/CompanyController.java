package com.softix.app_back.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @GetMapping("/admin")
    public Page<CompanyAdminResponse> findAdminCompanies(@RequestParam(required = false) CompanyType type,
                                                         @RequestParam(required = false) CompanyStatus status,
                                                         @RequestParam(required = false) String search,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {

        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "tradeName"));
        return companyService.findAdminCompanies(type, status, search, pageable);

    }


    @GetMapping("/admin/{id}")
    public CompanyAdminDetailResponse findAdminById(@PathVariable String id) {
        return companyService.findAdminById(id);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyAdminDetailResponse create(@RequestBody CompanySaveRequest request) {
        return companyService.save(request);
    }


    @PutMapping("/{id}")
    public CompanyAdminDetailResponse update(@PathVariable String id,
                                             @RequestBody CompanySaveRequest request) {
        return companyService.update(id, request);
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateMany(@RequestBody List<String> ids) {
        companyService.deactivateMany(ids);
    }

    @GetMapping("/companies/types")
    public List<CompanyTypeResponse> findCompanyTypes() {
        return companyService.findCompanyTypes();
    }

    @GetMapping("/companies/statuses")
    public List<String> findCompanyStatuses() {
        return companyService.findCompanyStatuses();
    }

    @GetMapping("/companies/payment-methods")
    public List<String> findPaymentMethods() {
        return companyService.findPaymentMethods();
    }

    @GetMapping("/companies/amenities")
    public List<String> findAmenities() {
        return companyService.findAmenities();
    }

    @GetMapping("/companies/home-page")
    public Page<CompanyResponse> findCompanies(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size,
                                               @RequestParam(required = false) CompanyType type,
                                               @RequestParam(required = false) String search) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return companyService.findPublicCompanies(type, search, pageRequest);

    }

}