package com.nexus.controller;

import com.nexus.constant.ClientConstant;
import com.nexus.dto.ClientDTO;
import com.nexus.dto.CreateClientDTO;
import com.nexus.dto.UserDTO;
import com.nexus.entity.Client;
import com.nexus.entity.HttpResponse;
import com.nexus.entity.User;
import com.nexus.service.ClientService;
import com.nexus.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

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

    @GetMapping("/get-all")
    private ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> clientsInDB = clientService.getAllClients();
        List<ClientDTO> clientsToReturn = clientsInDB.stream().map(this::convertToClientDTO).collect(Collectors.toList());
        return new ResponseEntity<>(clientsToReturn, OK);
    }

    @GetMapping("/get-client/{id}")
    private ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id){
        Client clientInDB = clientService.getClientById(id);
        return new ResponseEntity<>(convertToClientDTO(clientInDB), OK);
    }

    private ClientDTO convertToClientDTO(Client client){
        return mapper.map(client, ClientDTO.class);
    }
}