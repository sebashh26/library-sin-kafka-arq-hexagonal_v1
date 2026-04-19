package com.mitocode.library.infraestructure.in.web.dto.request.orderbook;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientIdRequest {
	@NotNull(message = "Client ID is required")
	private String id;

}
