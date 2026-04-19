package com.mitocode.library.application.rs.dto;

import com.mitocode.library.domain.model.entity.Client;

public record ClientRecordDto(String id, String name, String surname, String dni, Integer age) {

	public static ClientRecordDto fromDomain(Client client) {

		return new ClientRecordDto(client.getId(), client.getName(), client.getSurname(), client.getDni(),
				client.getAge());
	}
}
