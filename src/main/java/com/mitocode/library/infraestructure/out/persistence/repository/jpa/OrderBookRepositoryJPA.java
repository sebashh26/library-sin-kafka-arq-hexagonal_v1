package com.mitocode.library.infraestructure.out.persistence.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mitocode.library.infraestructure.out.persistence.entity.OrderBookEntity;

@Repository
public interface OrderBookRepositoryJPA extends IGenericRepo<OrderBookEntity, Integer> {

	//@Query("SELECT o FROM OrderBook o WHERE o.client.dni = :clientId")
	Optional<OrderBookEntity> findByClientDni(String clientId);
	
	//@Query("SELECT b FROM orerBook o JOIN o.books b WHERE o.client.dni = :dni AND b.bookStatus = com.mitocode.library.domain.model.enums.BookStatus.BORROWED")
	List<OrderBookEntity> findBorrowedBooksByClient_Dni(String dni);

}
