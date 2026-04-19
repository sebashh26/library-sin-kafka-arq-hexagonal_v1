package com.mitocode.library.infraestructure.in.web.dto.request.orderbook;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateOrderBookRequest {

	//@NotEmpty(message = "DateOrder is required")
	//private LocalDateTime dateOrder;
	@NotNull(message = "Client is required")
	private ClientIdRequest client;
	@NotNull(message = "Books is required")
	private List<BookIdRequest> books;
	//@NotEmpty(message = "OrderBookStatus is required")
	//private OrderBookStatus orderBookStatus;
}
