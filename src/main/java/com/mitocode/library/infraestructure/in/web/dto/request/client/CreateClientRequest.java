package com.mitocode.library.infraestructure.in.web.dto.request.client;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateClientRequest {
	
	@NotEmpty(message = "Name is required")
	@NotBlank(message = "Name is required")
	private String name;
	
	@NotEmpty(message = "Surname is required")
	@NotBlank(message = "Surname is required")
	private String surname;
	
	@NotEmpty(message = "DNI is required")
	@NotBlank(message = "DNI is required")
	private String dni;
	
	//@NotBlank(message = "Age is required")no use because is Integer
	@NotNull(message = "Age is required")
	@Min(value = 1, message = "Age must be greater than 0")
	private Integer age;

}
