package com.mitocode.library.application.usecase.book;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetDataByDNIQuery;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.port.out.persistence.OrderBookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetBookBorrowedByDniUseCase {

	private final OrderBookRepository orderBookRepository;
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;

	public List<BookRecordDto> execute(GetDataByDNIQuery query) {
		// Traer todas las órdenes completadas del cliente
		List<Book> borrowedBooks = orderBookRepository.findBorrowedBooksByClientDni(query.dni());
		return borrowedBooks.stream()
				.map(this::convertDomainToDto)
				.toList();
	}
	
	private BookRecordDto convertDomainToDto(Book book) {
		return BookRecordDto.fromDomain(book);
	}


}
