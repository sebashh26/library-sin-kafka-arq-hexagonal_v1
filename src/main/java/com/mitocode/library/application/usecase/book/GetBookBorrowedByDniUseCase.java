package com.mitocode.library.application.usecase.book;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetDataByDNIQuery;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.domain.model.entity.OrderBook;
import com.mitocode.library.domain.model.enums.BookStatus;
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
        List<OrderBook> orders = orderBookRepository.findBorrowedBooksByClientDni(query.dni());

        // De cada orden, obtener los libros alquilados
        return orders.stream()
                .flatMap(order -> order.getBooks().stream())
                .filter(book -> book.getBookStatus() == BookStatus.BORROWED)
                .map(book -> BookRecordDto.fromDomain(book))
                //.map(book -> new BookRecordDto(book.getId(), book.getTitle(), book.getIsbn(), book.getBookStatus()))
                .toList();
	}
	

}
