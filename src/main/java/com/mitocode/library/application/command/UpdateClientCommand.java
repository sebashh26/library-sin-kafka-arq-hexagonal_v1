package com.mitocode.library.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientCommand {

	private String id;
	private String name;
	private String surname;
	private String DNI;
	private Integer age;
}
