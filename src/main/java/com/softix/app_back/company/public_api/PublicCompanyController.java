package com.softix.app_back.company.public_api;

import com.softix.app_back.professional.ProfessionalRepository;
import com.softix.app_back.professional.ProfessionalStatus;
import com.softix.app_back.service_offering.ServiceOfferingRepository;
import com.softix.app_back.service_offering.ServiceOfferingStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/company")
public class PublicCompanyController {

    @Autowired
    ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    ProfessionalRepository professionalRepository;

    @GetMapping("/{companyId}/services")
    public List<PublicServiceOfferingDTO> findServices(@PathVariable String companyId) {
        return serviceOfferingRepository.findByCompanyIdAndStatusOrderByNameAsc(companyId, ServiceOfferingStatus.ACTIVE).stream().map(PublicServiceOfferingDTO::new).toList();
    }

    @GetMapping("/{companyId}/professionals")
    public List<PublicProfessionalDTO> findProfessionals(@PathVariable String companyId) {
        return professionalRepository.findByCompanyIdAndStatusOrderByPerson_NameAsc(companyId, ProfessionalStatus.ACTIVE).stream().map(PublicProfessionalDTO::new).toList();
    }

}