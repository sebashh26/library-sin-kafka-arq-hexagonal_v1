package com.mitocode.library.domain.port.out.persistence;

import java.util.List;
import java.util.Optional;

import com.mitocode.library.domain.model.entity.OrderBook;

public interface OrderBookRepository extends ICRUD<OrderBook, Integer> {
	
	Optional<OrderBook> findByClientDni(String clientId);
    List<OrderBook> findBorrowedBooksByClientDni(String dni);

	
	//@Query("SELECT b FROM Book b WHERE b.client.dni = :dni AND b.bookStatus = com.mitocode.model.BookStatus.BORROWED")
    //List<Book> findBorrowedBooksByClientDni(@Param("dni") String dni);

}
