package com.mitocode.library.infraestructure.in.messaging.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrderBookEventRequest {

	//@NotEmpty(message = "DateOrder is required")
	//private LocalDateTime dateOrder;
	String correlationId;
	String key;
	
	@NotNull(message = "Client is required")
	private ClientIdEventRequest client;
	@NotNull(message = "Books is required")
	private List<BookIdEventRequest> books;
	//@NotEmpty(message = "OrderBookStatus is required")
	//private OrderBookStatus orderBookStatus;
}
