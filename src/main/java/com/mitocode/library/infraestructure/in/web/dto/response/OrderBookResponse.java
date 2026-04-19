package com.mitocode.library.infraestructure.in.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.mitocode.library.domain.model.enums.OrderBookStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderBookResponse {

	Integer idOrderBook;
	private LocalDateTime dateOrder;
	private ClientResponse client;
    private List<BookResponse> books;
    private OrderBookStatus orderBookStatus;
}
