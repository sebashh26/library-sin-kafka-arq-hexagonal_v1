package com.mitocode.library.domain.model.entity;

import java.util.UUID;

import com.mitocode.library.domain.model.exception.ClientInvalidateException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
	
	private String id;
	private String name;
	private String surname;
	private String dni;
	private Integer age;
	
	public void validateRequiredFields() {
//		if (id == null) {
//			throw new ClientInvalidateException("ID is required");
//		}
		if (name == null) {
			throw new ClientInvalidateException("Name is required");
		}
		if (surname == null) {
			throw new ClientInvalidateException("Surname is required");
		}
		if (dni == null) {
			throw new ClientInvalidateException("DNI is required");
		}
	}
	
	public void validateAge() {
		if (age == null || age < 18) {
			throw new ClientInvalidateException("Client must be at least 18 years old");
		}
	}
	
	public void update(String name, String surname, String DNI, Integer age) {
        this.name = name;
        this.surname = surname;
        this.dni = DNI;
        this.age = age;
    }

	
	public void generateClientId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
