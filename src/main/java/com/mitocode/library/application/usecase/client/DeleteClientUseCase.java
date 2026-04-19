package com.mitocode.library.application.usecase.client;

import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetDataByIdQuery;
import com.mitocode.library.domain.port.out.persistence.ClientRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class DeleteClientUseCase {
	
	private final ClientRepository clientRepository;
		
	public void execute(GetDataByIdQuery query) {		
		clientRepository.delete(query.id());
	}

}
