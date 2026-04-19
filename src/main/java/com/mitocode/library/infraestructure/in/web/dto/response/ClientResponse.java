package com.mitocode.library.infraestructure.in.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientResponse {

	private String id;
	private String name;
	private String surname;
	private String dni;
	private Integer age;
}
