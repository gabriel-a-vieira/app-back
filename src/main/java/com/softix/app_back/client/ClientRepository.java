package com.softix.app_back.client;

import org.springframework.data.jpa.repository.JpaRepository;
import utils.model.tenant.NoTenantFilter;

public interface ClientRepository extends JpaRepository<Client, String> {

    @NoTenantFilter
    Client findClientByPersonName(String personName);

}
