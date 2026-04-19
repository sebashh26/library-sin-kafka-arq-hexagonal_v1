package com.mitocode.library.application.usecase.book;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetBooksAvailableQuery;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.port.out.persistence.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetAvailableBooksUseCase {
	
	private final BookRepository bookRepository;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	public List<BookRecordDto> execute(GetBooksAvailableQuery query) {
		
		/**@see GetBookBorrowedByDniUseCase have another form to map values in the recor */
		return bookRepository.findByStatusAvailable().stream()
				.map(this::convertDomainToDto).toList();
	}
	
	//@see GetBookByIdUseCase the explain because the record doesn't have a constructor with all the fields, so we do it this way
	private BookRecordDto convertDomainToDto(Book book) {
		return BookRecordDto.fromDomain(book);
	}

}
