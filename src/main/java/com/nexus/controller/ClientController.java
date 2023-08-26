package com.nexus.controller;

import com.nexus.constant.ClientConstant;
import com.nexus.dto.CreateClientDTO;
import com.nexus.entity.Client;
import com.nexus.entity.HttpResponse;
import com.nexus.service.ClientService;
import com.nexus.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/client")
public class ClientController {
    private ClientService clientService;
    private ModelMapper mapper;
    @Autowired
    public ClientController(ClientService clientService, ModelMapper mapper) {
        this.clientService = clientService;
        this.mapper = mapper;
    }
    @PostMapping("/create")
    @Operation(summary = "Create a Client",
            description = "Create a Client")
    private ResponseEntity<HttpResponse> createClient(@RequestBody CreateClientDTO createClientDTO){
        Client clientToSave = mapper.map(createClientDTO, Client.class);
        clientService.createClient(clientToSave);
        return ResponseUtility.buildResponse(ClientConstant.CREATION_DONE,CREATED);
    }
}
