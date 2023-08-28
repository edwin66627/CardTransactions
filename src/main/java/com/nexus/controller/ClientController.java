package com.nexus.controller;

import com.nexus.constant.ClientConstant;
import com.nexus.dto.ClientDTO;
import com.nexus.dto.CreateClientDTO;
import com.nexus.dto.UpdateClientDTO;
import com.nexus.dto.UserDTO;
import com.nexus.entity.Client;
import com.nexus.entity.HttpResponse;
import com.nexus.entity.User;
import com.nexus.service.ClientService;
import com.nexus.utils.ResponseUtility;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    @Operation(summary = "Create a Client", description = "Save a new Client data")
    private ResponseEntity<HttpResponse> createClient(@Valid @RequestBody CreateClientDTO createClientDTO){
        Client clientToSave = mapper.map(createClientDTO, Client.class);
        clientService.createClient(clientToSave);
        return ResponseUtility.buildResponse(ClientConstant.CREATION_DONE,CREATED);
    }

    @GetMapping("/get-all")
    @Operation(summary = "Get all Clients", description = "Get all Clients in Database")
    private ResponseEntity<List<ClientDTO>> getAllClients(){
        List<Client> clientsInDB = clientService.getAllClients();
        List<ClientDTO> clientsToReturn = clientsInDB.stream().map(this::convertToClientDTO).collect(Collectors.toList());
        return new ResponseEntity<>(clientsToReturn, OK);
    }

    @GetMapping("/get-client/{id}")
    @Operation(summary = "Get a Client", description = "Get a specific Client By Id")
    private ResponseEntity<ClientDTO> getClientById(@PathVariable("id") Long id){
        Client clientInDB = clientService.getClientById(id);
        return new ResponseEntity<>(convertToClientDTO(clientInDB), OK);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "Update a Client", description = "Update a specific fetched by Id")
    public ResponseEntity<HttpResponse> updateClient(@Valid @RequestBody UpdateClientDTO updateClientDTO, @PathVariable("id") Long id){
        clientService.updateClient(mapper.map(updateClientDTO, Client.class), id);
        return ResponseUtility.buildResponse(ClientConstant.UPDATE_DONE, OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a Client", description = "Delete a Client by Id")
    private ResponseEntity<HttpResponse> blockCard(
            @Parameter(description = "Client id field used to fetch a Client and delete it")
            @PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseUtility.buildResponse(ClientConstant.DELETE_DONE, OK);
    }
    private ClientDTO convertToClientDTO(Client client){
        return mapper.map(client, ClientDTO.class);
    }
}
