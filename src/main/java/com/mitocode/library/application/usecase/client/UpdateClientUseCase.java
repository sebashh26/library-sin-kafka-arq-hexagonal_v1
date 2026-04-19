package com.mitocode.library.application.usecase.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.command.UpdateClientCommand;
import com.mitocode.library.application.rs.dto.ClientRecordDto;
import com.mitocode.library.domain.model.entity.Client;
import com.mitocode.library.domain.port.out.persistence.ClientRepository;
import com.mitocode.library.infraestructure.out.persistence.exception.ModelNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class UpdateClientUseCase {

	private final ClientRepository clientRepository;

	@Qualifier("defaultMapper")
	private final ModelMapper mapper;

	public ClientRecordDto execute(UpdateClientCommand updateClientCommand) {

		Client existingClient = clientRepository.findById(updateClientCommand.getId())
				.orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + updateClientCommand.getId()));

		// 2. Validar reglas de negocio sobre el estado actual
		existingClient.validateAge();

		// 3. Aplicar cambios del comando
		existingClient.update(updateClientCommand.getName(), updateClientCommand.getSurname(),
				updateClientCommand.getDNI(), updateClientCommand.getAge());

		existingClient.validateRequiredFields();

		Client clientResult = clientRepository.update(existingClient);

		return convertDomainToDto(clientResult);

	}

	private ClientRecordDto convertDomainToDto(Client client) {
		return ClientRecordDto.fromDomain(client);
	}

}
