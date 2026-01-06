package com.softix.app_back.service_offering;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceOfferingRepository extends JpaRepository<ServiceOffering, UUID> {



}
