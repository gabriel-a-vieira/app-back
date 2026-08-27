package com.softix.app_back.service_offering;

import com.softix.app_back.company.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import utils.security.SecurityUtils;

import java.util.List;

@Service
public class ServiceOfferingService {

    @Autowired
    ServiceOfferingRepository serviceOfferingRepository;

    @Autowired
    CompanyRepository companyRepository;

    public Page<ServiceOfferingDTO> findAll(String search, String status, Integer minDuration, Integer maxDuration, Double minPrice, Double maxPrice, String companyId, Pageable pageable) {

        String resolvedCompanyId = SecurityUtils.resolveCompanyId(companyId);

        ServiceOfferingStatus parsedStatus = parseStatus(status);

        return serviceOfferingRepository.findAdvanced(resolvedCompanyId, search, parsedStatus, minDuration, maxDuration, minPrice, maxPrice, pageable).map(ServiceOfferingDTO::new);

    }

    public ServiceOfferingDTO findById(String id) {

        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico nao encontrado"));

        return new ServiceOfferingDTO(serviceOffering);

    }

    @Transactional
    public ServiceOfferingDTO save(ServiceOfferingDTO dto) {

        String companyId = SecurityUtils.resolveCompanyId(dto.getCompanyId());

        String name = dto.getName().trim();

        if (serviceOfferingRepository.existsByCompanyIdAndNameIgnoreCase(companyId, name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja existe um servico com este nome nesta empresa");
        }

        ServiceOffering serviceOffering = new ServiceOffering();

        serviceOffering.setCompanyId(companyId);
        serviceOffering.setName(name);
        serviceOffering.setDescription(normalize(dto.getDescription()));
        serviceOffering.setDurationMinutes(dto.getDurationMinutes());
        serviceOffering.setPrice(dto.getPrice());

        serviceOffering.setStatus(dto.getStatus() != null ? dto.getStatus() : ServiceOfferingStatus.ACTIVE);

        serviceOfferingRepository.save(serviceOffering);

        return new ServiceOfferingDTO(serviceOffering);

    }

    @Transactional
    public ServiceOfferingDTO update(String id, ServiceOfferingDTO dto) {

        ServiceOffering serviceOffering = serviceOfferingRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Servico nao encontrado"));

        String name = dto.getName().trim();

        if (serviceOfferingRepository.existsByCompanyIdAndNameIgnoreCaseAndIdNot(serviceOffering.getCompanyId(), name, id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ja existe outro servico com este nome nesta empresa");
        }

        serviceOffering.setName(name);

        serviceOffering.setDescription(normalize(dto.getDescription()));

        serviceOffering.setDurationMinutes(dto.getDurationMinutes());

        serviceOffering.setPrice(dto.getPrice());

        if (dto.getStatus() != null) {
            serviceOffering.setStatus(dto.getStatus());
        }

        serviceOfferingRepository.save(serviceOffering);

        return new ServiceOfferingDTO(serviceOffering);

    }

    @Transactional
    public void deleteMany(List<String> ids) {

        if (ids == null || ids.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum servico informado");
        }

        List<ServiceOffering> services = serviceOfferingRepository.findByIdIn(ids);

        for (ServiceOffering serviceOffering : services) {
            serviceOffering.setStatus(ServiceOfferingStatus.INACTIVE);
        }

        serviceOfferingRepository.saveAll(services);

    }

    private ServiceOfferingStatus parseStatus(String status) {

        if (status == null || status.isBlank() || "ALL".equalsIgnoreCase(status)) {
            return null;
        }

        try {
            return ServiceOfferingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status invalido");
        }
    }

    private String normalize(String value) {

        if (value == null) {
            return null;
        }

        String normalized = value.trim();

        return normalized.isEmpty() ? null : normalized;

    }

}