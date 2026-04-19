package com.mitocode.library.infraestructure.in.web.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.library.infraestructure.in.web.dto.request.client.CreateClientRequest;
import com.mitocode.library.infraestructure.in.web.dto.request.client.UpdateClientRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.ClientResponse;
import com.mitocode.library.infraestructure.in.web.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Client Controller", description = "Clients management endpoints")
public class ClientController {
	
	private final ClientService clientService;
	
	@PostMapping
    @Operation(summary = "Create a new client", description = "Creates a new client in the system with http protocol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Client created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Client already exists")
    })	
	 public ResponseEntity<ClientResponse> create(@Valid @RequestBody CreateClientRequest request){
		ClientResponse response  = clientService.createClient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@GetMapping("/{id}")
    @Operation(summary = "Get client by ID", description = "Retrieves a client by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResponse> findById(@PathVariable("id") String idClient){
        return ResponseEntity.ok(clientService.findByIdClient(idClient));
    }
	
	@GetMapping("/dni/{dni}")
    @Operation(summary = "Get client by ID", description = "Retrieves a client by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client found"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<ClientResponse> findByDni(@PathVariable("dni") String dniClient){
        return ResponseEntity.ok(clientService.findByDniClient(dniClient));
    }
	
	@PutMapping("/{id}")
    @Operation(summary = "Update client", description = "Updates the information of a client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client updated successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found"),
            @ApiResponse(responseCode = "400", description = "Client update data")
    })
	public ResponseEntity<ClientResponse> update( @PathVariable String id, @Valid @RequestBody UpdateClientRequest request){
		request.setId(id);
		ClientResponse response =  clientService.updateClient(request);
        return ResponseEntity.ok(response);
    }
	
	@DeleteMapping("/{id}")
    @Operation(summary = "Delete client", description = "no delete only desactive")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Client deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Client not found")
    })
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
		clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
