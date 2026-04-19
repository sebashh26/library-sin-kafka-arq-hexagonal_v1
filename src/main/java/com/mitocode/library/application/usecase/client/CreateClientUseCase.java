package com.mitocode.library.application.usecase.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.command.CreateClientCommand;
import com.mitocode.library.application.exception.InvalidRequestException;
import com.mitocode.library.application.rs.dto.ClientRecordDto;
import com.mitocode.library.domain.model.entity.Client;
import com.mitocode.library.domain.port.out.persistence.ClientRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class CreateClientUseCase {
	
	private final ClientRepository clientRepository;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	public ClientRecordDto execute(CreateClientCommand createClientCommand) {
		
		Client client = convertCommandToDomain(createClientCommand);
		
		if(clientRepository.existsByDni(createClientCommand.getDNI())){
			throw new InvalidRequestException("DNI already exists");
		}
				
		
		client.validateRequiredFields();
		client.validateAge();
		client.generateClientId();		
		
		Client clientResult = clientRepository.save(client);
		
		return convertDomainToDto(clientResult);
		
	}
	
	private Client convertCommandToDomain(CreateClientCommand clienteCommand) {
		return mapper.map(clienteCommand, Client.class);
	}

	private ClientRecordDto convertDomainToDto(Client client) {
		return ClientRecordDto.fromDomain(client);
	}
	
	
	

}
