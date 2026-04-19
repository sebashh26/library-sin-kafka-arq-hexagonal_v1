package com.mitocode.library.domain.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.mitocode.library.domain.model.enums.OrderBookStatus;
import com.mitocode.library.domain.model.exception.OrderBookInvalidateException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderBook {
	
	
	private Integer idOrderBook;//a este si quiero generarle un id incremental en la entidad
	private LocalDateTime dateOrder;
	private Client client;
    private List<Book> books;
    private OrderBookStatus orderBookStatus;
    
    public void completeOrder(Client client, List<Book> books) {
        this.client = client;
        this.books = books;
        this.orderBookStatus = OrderBookStatus.COMPLETED;
        this.dateOrder = LocalDateTime.now();
        this.setBookBorrow();
    }

    
    public void validateRequiredFields() {
		if (client == null) {
			throw new OrderBookInvalidateException("Client is required");
		}
		if (books == null || books.isEmpty()) {
			throw new OrderBookInvalidateException("At least one book is required");
		}
//		if (orderBookStatus == null) {
//			throw new OrderBookInvalidateException("OrderBookStatus is required");
//		}
	}
    
    public void setBookBorrow() {
		for (Book book : books) {
			book.borrowBook();
		}
	}
		
}
