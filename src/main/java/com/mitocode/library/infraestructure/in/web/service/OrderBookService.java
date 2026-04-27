package com.mitocode.library.infraestructure.in.web.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mitocode.library.application.command.orderbook.CreateOrderBookCommand;
import com.mitocode.library.application.query.GetDataByDNIQuery;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.application.rs.dto.OrderBookRecordDto;
import com.mitocode.library.application.usecase.book.CreateOrderBookUseCase;
import com.mitocode.library.application.usecase.book.GetBookBorrowedByDniUseCase;
import com.mitocode.library.application.usecase.book.GetOrderByDniClientUseCase;
import com.mitocode.library.application.usecase.book.GetOrderByIdUseCase;
import com.mitocode.library.infraestructure.in.web.dto.request.orderbook.CreateOrderBookRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.BookResponse;
import com.mitocode.library.infraestructure.in.web.dto.response.OrderBookResponse;

import lombok.RequiredArgsConstructor;

//este es un Bol que agrupra varios casos de uso y se inyecta en el controlador para su uso
@RequiredArgsConstructor
@Service
public class OrderBookService {

	private final CreateOrderBookUseCase createOrderBookUseCase;
	private final GetOrderByDniClientUseCase getOrderBookByDniClientUseCase;
	private final GetOrderByIdUseCase getOrderBookByIdUseCase;
	private final GetBookBorrowedByDniUseCase getBookBorrowedByDniUseCase;

	@Qualifier("orderBookMapper")
	private final ModelMapper mapper;
	
	@Qualifier("bookMapper")
	private final ModelMapper bookMapper;

	public OrderBookResponse createOrderBook(CreateOrderBookRequest request) {
		CreateOrderBookCommand command = mapper.map(request, CreateOrderBookCommand.class);
		OrderBookRecordDto dto = createOrderBookUseCase.execute(command);
		return mapper.map(dto, OrderBookResponse.class);
	}

	public OrderBookResponse findByIdOrderBook(Integer id) {
		OrderBookRecordDto dto = getOrderBookByIdUseCase.execute(id);
		return mapper.map(dto, OrderBookResponse.class);
	}

	public OrderBookResponse findByDniClientOrderBook(String dni) {
		OrderBookRecordDto dto = getOrderBookByDniClientUseCase
				.execute(new GetDataByDNIQuery(dni));
		return mapper.map(dto, OrderBookResponse.class);
	}
	
	public List<BookResponse> findBookBorrowedByDni(String dni) {
		List<BookRecordDto> dto = getBookBorrowedByDniUseCase
				.execute(new GetDataByDNIQuery(dni));
		return dto.stream()
				.map(bookDto -> bookMapper.map(bookDto, BookResponse.class)).toList();
	}

}
