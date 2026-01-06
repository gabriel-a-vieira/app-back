package com.softix.app_back.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String> {

    Client findClientByPersonName(String personName);

}
