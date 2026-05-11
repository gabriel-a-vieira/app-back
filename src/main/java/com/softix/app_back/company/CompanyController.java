package com.softix.app_back.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRepository companyRepository;

    @GetMapping
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @GetMapping(path = "{id}")
    public Company findById(@PathVariable("id") String id) {
        return companyRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity post(@RequestBody String jsonBody) {

        if (jsonBody == null) return ResponseEntity.badRequest().body("Invalid JSON");

        ObjectMapper objectMapper = new ObjectMapper();
        CompanyDTO dto;

        try {
            dto = objectMapper.readValue(jsonBody, CompanyDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error parsing JSON");
        }

        return ResponseEntity.ok(companyService.save(dto));

    }

    @GetMapping("/companies/home-page")
    public Page<CompanyResponse> findCompanies(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "8") int size,
                                               @RequestParam(required = false) CompanyType type,
                                               @RequestParam(required = false) String search) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        return companyService.findPublicCompanies(type, search, pageRequest);

    }

    @GetMapping("/companies/types")
    public List<CompanyTypeResponse> findCompanyTypes() {
        return companyService.findCompanyTypes();
    }

}