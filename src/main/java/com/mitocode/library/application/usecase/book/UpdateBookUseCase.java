package com.mitocode.library.application.usecase.book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.library.application.command.UpdateBookCommand;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.port.out.persistence.BookRepository;
import com.mitocode.library.infraestructure.out.persistence.exception.ModelNotFoundException;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Component
public class UpdateBookUseCase {
	
	private final BookRepository bookRepository;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	public BookRecordDto execute(UpdateBookCommand updateBookCommand) {
		
		Book existingBook = bookRepository.findById(updateBookCommand.getId())
		        .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + updateBookCommand.getId()));

		existingBook.validateAvailable();

		existingBook.update(
		        updateBookCommand.getTitle(),
		        updateBookCommand.getAuthor(),
		        updateBookCommand.getIsbn()
		    );
		
		existingBook.validateRequiredFields();
		
		Book bookResult = bookRepository.update(existingBook);
		
		return convertDomainToDto(bookResult);
		
	}

	//@see GetBookByIdUseCase the explain because the record doesn't have a constructor with all the fields, so we do it this way
	private BookRecordDto convertDomainToDto(Book book) {
		return BookRecordDto.fromDomain(book);
	}

}
