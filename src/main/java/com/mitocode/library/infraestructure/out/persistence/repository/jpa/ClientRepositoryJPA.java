package com.mitocode.library.infraestructure.out.persistence.repository.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mitocode.library.infraestructure.out.persistence.entity.ClientEntity;

@Repository
public interface ClientRepositoryJPA extends IGenericRepo<ClientEntity, String> {
	
	boolean existsByDni(String DNI);
	Optional<ClientEntity> findByDni(String DNI);	

}
