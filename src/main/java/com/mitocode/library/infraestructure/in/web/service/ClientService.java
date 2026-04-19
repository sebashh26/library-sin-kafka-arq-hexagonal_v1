package com.mitocode.library.infraestructure.in.web.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mitocode.library.application.command.CreateClientCommand;
import com.mitocode.library.application.command.UpdateClientCommand;
import com.mitocode.library.application.query.GetDataByDNIQuery;
import com.mitocode.library.application.query.GetDataByIdQuery;
import com.mitocode.library.application.rs.dto.ClientRecordDto;
import com.mitocode.library.application.usecase.client.CreateClientUseCase;
import com.mitocode.library.application.usecase.client.DeleteClientUseCase;
import com.mitocode.library.application.usecase.client.GetClientByDNIUseCase;
import com.mitocode.library.application.usecase.client.GetClientByIdUserCase;
import com.mitocode.library.application.usecase.client.UpdateClientUseCase;
import com.mitocode.library.infraestructure.in.web.dto.request.client.CreateClientRequest;
import com.mitocode.library.infraestructure.in.web.dto.request.client.UpdateClientRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.ClientResponse;

import lombok.RequiredArgsConstructor;

//este es un Bol que agrupra varios casos de uso y se inyecta en el controlador para su uso
@RequiredArgsConstructor
@Service
public class ClientService {
	
	private final CreateClientUseCase createClientUseCase;
	private final GetClientByDNIUseCase getClientByDNIUseCase;
	private final GetClientByIdUserCase getClientByIdUserCase;
	private final UpdateClientUseCase updateClientUseCase;
	private final DeleteClientUseCase deleteClientUseCase;
	
	@Qualifier("clientMapper")
	private final ModelMapper mapper;
	
	public ClientResponse createClient(CreateClientRequest request) {
		CreateClientCommand command = mapper.map(request, CreateClientCommand.class);
		ClientRecordDto dto = createClientUseCase.execute(command);
		return mapper.map(dto, ClientResponse.class);
	}
	
	public ClientResponse updateClient(UpdateClientRequest request){
		UpdateClientCommand command = mapper.map(request, UpdateClientCommand.class);
		ClientRecordDto dto = updateClientUseCase.execute(command);
		return mapper.map(dto, ClientResponse.class);
	}
	
	public ClientResponse findByDniClient(String dni) {
		ClientRecordDto dto = getClientByDNIUseCase.execute(new GetDataByDNIQuery(dni));
		return mapper.map(dto, ClientResponse.class);
	}
	
	public ClientResponse findByIdClient(String id) {
		ClientRecordDto dto = getClientByIdUserCase.execute(new GetDataByIdQuery(id));
		return mapper.map(dto, ClientResponse.class);
	}

	public void  deleteClient(String id) {
		deleteClientUseCase.execute(new GetDataByIdQuery(id.toString()));		
	}

}
