package com.softix.app_back.service_offering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceOfferingService {

    @Autowired
    ServiceOfferingRepository serviceOfferingRepository;

    @Transactional
    public ServiceOffering save(ServiceOfferingDTO dto) {

        ServiceOffering serviceOffering = new ServiceOffering();

        serviceOffering.setName(dto.getName());
        serviceOffering.setDescription(dto.getDescription());
        serviceOffering.setDurationMinutes(dto.getDurationMinutes());
        serviceOffering.setPrice(dto.getPrice());

        return serviceOfferingRepository.save(serviceOffering);

    }

}
