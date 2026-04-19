package com.mitocode.library.infraestructure.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class ClientEntity {

	@Id
	@Column(name = "id_client", length = 36)
	private String id;
	@Column(name = "name", nullable = false, length = 55)
	private String name;
	@Column(name = "surname", nullable = false, length = 55)
	private String surname;
	@Column(name = "dni", nullable = false, length = 20)
	private String dni;
	@Column(name = "age", nullable = false)
	private Integer age;
}
