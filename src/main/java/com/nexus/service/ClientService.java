package com.nexus.service;

import com.nexus.entity.Client;
import com.nexus.entity.User;

import java.util.List;

public interface ClientService {
    Client createClient(Client client);
    List<Client> getAllClients();
    Client getClientById(Long id);
    void updateClient(Client client, Long id);
    void deleteClient(Long id);

}
