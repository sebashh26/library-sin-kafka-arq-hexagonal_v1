package com.mitocode.library.infraestructure.out.persistence.repository.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mitocode.library.infraestructure.out.persistence.entity.BookEntity;
import com.mitocode.library.infraestructure.out.persistence.entity.OrderBookEntity;

@Repository
public interface OrderBookRepositoryJPA extends IGenericRepo<OrderBookEntity, Integer> {

	Optional<OrderBookEntity> findByClientDni(String clientId);

	@Query("SELECT b FROM OrderBookEntity o " + "JOIN o.books b " + "JOIN o.client c "
			+ "WHERE c.dni = :dni AND b.bookStatus = com.mitocode.library.domain.model.enums.BookStatus.BORROWED")
	List<BookEntity> findBorrowedBooksByClientDni(@Param("dni") String dni);

}
