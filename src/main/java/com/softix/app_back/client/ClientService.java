package com.softix.app_back.client;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientService {

    @Transactional
    public Client save(ClientDTO dto) {

        Client client = new Client();

        //TODO develop this method, instancing fields and related entities and then come to back to the controller to call this method

        return client;

    }

}
