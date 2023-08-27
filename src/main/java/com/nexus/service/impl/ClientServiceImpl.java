package com.nexus.service.impl;

import com.nexus.constant.ClientConstant;
import com.nexus.entity.Client;
import com.nexus.entity.User;
import com.nexus.repository.ClientRepository;
import com.nexus.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Client createClient(Client client) {
        Calendar currentDate = Calendar.getInstance();
        client.setJoinDate(currentDate.getTime());
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id){
        Client clientInDB = clientRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format(ClientConstant.NO_SUCH_ELEMENT, "id", id)));
        return clientInDB;
    }
}
