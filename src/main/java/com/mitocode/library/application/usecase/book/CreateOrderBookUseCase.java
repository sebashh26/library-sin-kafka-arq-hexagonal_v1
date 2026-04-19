package com.mitocode.library.application.usecase.book;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.command.orderbook.BookIdCommand;
import com.mitocode.library.application.command.orderbook.CreateOrderBookCommand;
import com.mitocode.library.application.exception.BookAlreadyBorrowedException;
import com.mitocode.library.application.exception.BookNotFoundException;
import com.mitocode.library.application.exception.ClientNotFoundException;
import com.mitocode.library.application.rs.dto.OrderBookRecordDto;
import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.model.entity.Client;
import com.mitocode.library.domain.model.entity.OrderBook;
import com.mitocode.library.domain.port.out.persistence.BookRepository;
import com.mitocode.library.domain.port.out.persistence.ClientRepository;
import com.mitocode.library.domain.port.out.persistence.OrderBookRepository;

import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CreateOrderBookUseCase {

	private final OrderBookRepository orderBookRepository;
	private final ClientRepository clientRepository;
	private final BookRepository bookRepository;

	@Qualifier("defaultMapper")
	private final ModelMapper mapper;

	public OrderBookRecordDto execute(CreateOrderBookCommand orderBookCommand) {

		// Validar cliente
		Client client = clientRepository.findById(orderBookCommand.getClient().getId()).orElseThrow(
				() -> new ClientNotFoundException("Client not found: " + orderBookCommand.getClient().getId()));

		// Validar libros
		List<Book> validatedBooks = validateBooks(orderBookCommand.getBooks());
		OrderBook orderBook = convertCommandToDomain(orderBookCommand);
		orderBook.validateRequiredFields();
		// Reemplazar con entidades validadas
		orderBook.completeOrder(client, validatedBooks);

		try {

			// Guardar pedido
			OrderBook orderBookResult = orderBookRepository.save(orderBook);
			validatedBooks.forEach(bookRepository::update);

			// Convertir a DTO
			return convertDomainToDto(orderBookResult);
			// lo siguiente es ver si ottro proceso ya cambio el estado del libro y no se
			// pudo completar la orden
		} catch (OptimisticLockException e) {
			throw new BookAlreadyBorrowedException(
					"One of the selected books has already been borrowed by another user.");
		}

	}

	private List<Book> validateBooks(List<BookIdCommand> books) {
		return books.stream().map(book -> bookRepository.findById(book.getId())
				.orElseThrow(() -> new BookNotFoundException("Book not found with ID: " + book.getId()))).map(book -> {
					if (book.isBorrowed()) {
						throw new BookAlreadyBorrowedException("The book '" + book.getTitle() + "' (ID: " + book.getId()
								+ ") has already been borrowed.");

					}
					return book;
				}).toList();
	}

	private OrderBook convertCommandToDomain(CreateOrderBookCommand orderBookCommand) {
		return mapper.map(orderBookCommand, OrderBook.class);
	}

	private OrderBookRecordDto convertDomainToDto(OrderBook orderBook) {
		return OrderBookRecordDto.fromDomain(orderBook);
	}
	// usaria lo siguiente si quiero mapper dedicado y no quiero enviar objetos
	// completos y solo valores puntuales
//	@Component
//	public class OrderBookDtoMapper {
//	    public OrderBookRecordDto toDto(OrderBook orderBook) {
//	        return new OrderBookRecordDto(
//	            orderBook.getId(),
//	            orderBook.getClient().getName(),
//	            orderBook.getBooks().stream()
//	                     .map(Book::getTitle)
//	                     .toList()
//	        );
//	    }
//	}

}
