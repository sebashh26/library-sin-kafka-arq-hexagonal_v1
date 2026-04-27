package com.mitocode.library.infraestructure.out.persistence.adapter.jpa;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.model.entity.OrderBook;
import com.mitocode.library.domain.port.out.persistence.OrderBookRepository;
import com.mitocode.library.infraestructure.out.persistence.adapter.CRUDImpl;
import com.mitocode.library.infraestructure.out.persistence.entity.OrderBookEntity;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.IGenericRepo;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.OrderBookRepositoryJPA;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderBookRepositoryJPAAdapter extends CRUDImpl<OrderBook, Integer, OrderBookEntity> implements OrderBookRepository {
	
	private final OrderBookRepositoryJPA repo;
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;

	@Override
	protected IGenericRepo<OrderBookEntity, Integer> getRepo() {
		return repo;
	}

	@Override
	protected ModelMapper getMapper() {
		return mapper;
	}

	@Override
	protected Class<OrderBookEntity> getEntityClass() {
		return OrderBookEntity.class;
	}

	@Override
	protected Class<OrderBook> getDomainClass() {
		return OrderBook.class;
	}
	
	@Override
	public Optional<OrderBook> findByClientDni(String clientId) {
		return repo.findByClientDni(clientId).map(e -> mapper.map(e, OrderBook.class));
	}

	@Override
	public List<Book> findBorrowedBooksByClientDni(String dni) {
		return repo.findBorrowedBooksByClientDni(dni).stream()
				.map(e -> mapper.map(e, Book.class))
				.toList();
	}

	@Override
	protected void setEntityId(OrderBookEntity entity, Integer id) {
		entity.setIdOrderBook(id);
		
	}

}
