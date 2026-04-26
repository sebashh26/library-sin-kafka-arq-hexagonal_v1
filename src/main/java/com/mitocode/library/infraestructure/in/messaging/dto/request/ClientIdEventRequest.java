package com.mitocode.library.infraestructure.in.messaging.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientIdEventRequest {
	@NotNull(message = "Client ID is required")
	private String id;

}
