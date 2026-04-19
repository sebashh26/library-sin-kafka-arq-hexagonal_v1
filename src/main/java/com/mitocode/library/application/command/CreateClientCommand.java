package com.mitocode.library.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientCommand {

	//private String id; //no va ya que lo genero en el dominio, quize hacer eso peero en el execute no tengop como buscar
	private String name;
	private String surname;
	private String DNI;
	private Integer age;
}
