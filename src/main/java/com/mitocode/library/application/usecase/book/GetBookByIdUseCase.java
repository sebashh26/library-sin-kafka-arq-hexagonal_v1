package com.mitocode.library.application.usecase.book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetDataByIdQuery;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.port.out.persistence.BookRepository;
import com.mitocode.library.infraestructure.out.persistence.exception.ModelNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetBookByIdUseCase {
	
	private final BookRepository bookRepository;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	public BookRecordDto execute(GetDataByIdQuery query) {
		
		Book book = bookRepository.findById(query.id())
		        .orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + query.id()));
		
		return convertDomainToDto(book);
		
	}
	
	private BookRecordDto convertDomainToDto(Book book) {
		//return mapper.map(book, BookRecordDto.class); no funciona porque el record no tiene un constructor con todos los campos, por eso se hace de esta forma
		/**Un record en Java genera automáticamente un constructor canónico con todos los parámetros, pero no genera un constructor vacío.
		🔍 Qué hace ModelMapper
		Cuando usas:
		BookRecordDto dto = modelMapper.map(book, BookRecordDto.class);


		ModelMapper intenta:
		- Crear una instancia con new BookRecordDto() (constructor vacío).
		- Luego setear cada campo con setters.
		Pero:
		- Los records no tienen constructor vacío.
		- Los records no tienen setters (son inmutables).*/

		return BookRecordDto.fromDomain(book);
	}

}
