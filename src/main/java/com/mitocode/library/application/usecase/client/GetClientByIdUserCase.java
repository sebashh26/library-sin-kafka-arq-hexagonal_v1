package com.mitocode.library.application.usecase.client;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetDataByIdQuery;
import com.mitocode.library.application.rs.dto.ClientRecordDto;
import com.mitocode.library.domain.model.entity.Client;
import com.mitocode.library.domain.model.exception.ClientInvalidateException;
import com.mitocode.library.domain.port.out.persistence.ClientRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetClientByIdUserCase {
	
	private final ClientRepository clientRepository;

	@Qualifier("defaultMapper")
	private final ModelMapper mapper;

	public ClientRecordDto execute(GetDataByIdQuery query) {

		Client client = clientRepository.findById(query.id())
				.orElseThrow(() -> new ClientInvalidateException("Client not found: " + query.id()));

		return convertDomainToDto(client);

	}

	private ClientRecordDto convertDomainToDto(Client client) {
		return ClientRecordDto.fromDomain(client);

	}

}
