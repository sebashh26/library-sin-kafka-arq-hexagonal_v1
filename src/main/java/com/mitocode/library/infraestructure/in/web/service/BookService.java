package com.mitocode.library.infraestructure.in.web.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mitocode.library.application.command.CreateBookCommand;
import com.mitocode.library.application.command.UpdateBookCommand;
import com.mitocode.library.application.query.GetBooksAvailableQuery;
import com.mitocode.library.application.query.GetDataByIdQuery;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.application.usecase.book.CreateBookUseCase;
import com.mitocode.library.application.usecase.book.GetAvailableBooksUseCase;
import com.mitocode.library.application.usecase.book.GetBookByIdUseCase;
import com.mitocode.library.application.usecase.book.UpdateBookUseCase;
import com.mitocode.library.infraestructure.in.web.dto.request.book.CreateBookRequest;
import com.mitocode.library.infraestructure.in.web.dto.request.book.UpdateBookRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.BookResponse;

import lombok.RequiredArgsConstructor;

//este es un Bol que agrupra varios casos de uso y se inyecta en el controlador para su uso
@RequiredArgsConstructor
@Service
public class BookService {
	
	private final CreateBookUseCase createBookUseCase;
	private final GetAvailableBooksUseCase getAvailableBooksUseCase;
	private final UpdateBookUseCase updateBookUseCase;
	private final GetBookByIdUseCase getBookByIdUseCase;
	
	@Qualifier("bookMapper")
	private final ModelMapper mapper;
	
	public BookResponse createBook(CreateBookRequest request) {
		CreateBookCommand command = mapper.map(request, CreateBookCommand.class);
		BookRecordDto dto = createBookUseCase.execute(command);
		return mapper.map(dto, BookResponse.class);
	}
	
	public BookResponse updateBook(UpdateBookRequest request){
		UpdateBookCommand command = mapper.map(request, UpdateBookCommand.class);
		BookRecordDto dto = updateBookUseCase.execute(command);
		return mapper.map(dto, BookResponse.class);
	}
	
	public BookResponse findByIdBook(String id) {
		BookRecordDto dto = getBookByIdUseCase.execute(new GetDataByIdQuery(id));
		return mapper.map(dto, BookResponse.class);
	}
	
	public List<BookResponse> findAllAvailableBooks() {
		List<BookRecordDto> dtos = getAvailableBooksUseCase.execute(new GetBooksAvailableQuery());
		return dtos.stream()
				.map(dto -> mapper.map(dto, BookResponse.class)).toList();
	}
}
