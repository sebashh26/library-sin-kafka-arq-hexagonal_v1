package com.mitocode.library.application.rs.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.model.entity.Client;
import com.mitocode.library.domain.model.entity.OrderBook;
import com.mitocode.library.domain.model.enums.OrderBookStatus;

public record OrderBookRecordDto(
		Integer idOrderBook,  // identificador del pedido, lo coloco para que retone
	    LocalDateTime dateOrder,   // fecha de creación/registro
	    Client client,              // referencia al cliente
	    List<Book> books,           // lista de libros asociados
	    OrderBookStatus orderBookStatus // estado del pedido
	) {
	
	public static OrderBookRecordDto fromDomain(OrderBook orderBook) {
		return new OrderBookRecordDto(
				orderBook.getIdOrderBook(),
				orderBook.getDateOrder(),
				orderBook.getClient(),
				orderBook.getBooks(),
				orderBook.getOrderBookStatus()
		);
	}
}
