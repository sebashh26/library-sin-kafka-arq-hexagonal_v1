package com.mitocode.library.domain.port.out.persistence;

import java.util.Optional;

import com.mitocode.library.domain.model.entity.Client;

public interface ClientRepository extends ICRUD<Client, String> {
	
	boolean existsByDni(String DNI);
	Optional<Client> findByDni(String DNI);

}
