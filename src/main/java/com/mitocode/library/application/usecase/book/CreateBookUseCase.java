package com.mitocode.library.application.usecase.book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.command.CreateBookCommand;
import com.mitocode.library.application.exception.InvalidRequestException;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.port.out.persistence.BookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CreateBookUseCase {
	
	private final BookRepository bookRepository;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	public BookRecordDto execute(CreateBookCommand createBookCommand)  {
		
		Book book = convertCommandToDomain(createBookCommand);
		
		if(bookRepository.existsByIsbn(createBookCommand.getIsbn())) {
			throw new InvalidRequestException("ISBN already exists");
		}
		
		book.validateRequiredFields();
		book.activeAvailableBook();
		book.generateBookId();		
		
		Book bookResult = bookRepository.save(book);
		
		return convertDomainToDto(bookResult);
		
	}
	
	private Book convertCommandToDomain(CreateBookCommand bookCommand) {
		return mapper.map(bookCommand, Book.class);
	}

	private BookRecordDto convertDomainToDto(Book book) {
		return BookRecordDto.fromDomain(book);

	}

}
