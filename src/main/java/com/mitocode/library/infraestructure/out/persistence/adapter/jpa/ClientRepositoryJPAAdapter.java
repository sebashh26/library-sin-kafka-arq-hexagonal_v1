package com.mitocode.library.infraestructure.out.persistence.adapter.jpa;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.domain.model.entity.Client;
import com.mitocode.library.domain.port.out.persistence.ClientRepository;
import com.mitocode.library.infraestructure.out.persistence.adapter.CRUDImpl;
import com.mitocode.library.infraestructure.out.persistence.entity.ClientEntity;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.ClientRepositoryJPA;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.IGenericRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClientRepositoryJPAAdapter extends CRUDImpl<Client, String, ClientEntity> implements ClientRepository {
	
	private final ClientRepositoryJPA repo;
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;

	@Override
	protected IGenericRepo<ClientEntity, String> getRepo() {
		return repo;
	}

	@Override
	protected ModelMapper getMapper() {
		return mapper;
	}

	@Override
	protected Class<ClientEntity> getEntityClass() {
		return ClientEntity.class;
	}

	@Override
	protected Class<Client> getDomainClass() {
		return Client.class;
	}

	@Override
	protected void setEntityId(ClientEntity entity, String id) {
		entity.setId(id);
	}
	

	@Override
	public boolean existsByDni(String DNI) {
		return repo.existsByDni(DNI);
	}

	@Override
	public Optional<Client> findByDni(String DNI) {
		return repo.findByDni(DNI).map(e -> mapper.map(e, Client.class));
	}

}
