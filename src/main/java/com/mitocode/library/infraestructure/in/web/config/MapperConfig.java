package com.mitocode.library.infraestructure.in.web.config;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mitocode.library.application.command.orderbook.BookIdCommand;
import com.mitocode.library.application.command.orderbook.ClientIdCommand;
import com.mitocode.library.application.command.orderbook.CreateOrderBookCommand;
import com.mitocode.library.application.rs.dto.BookRecordDto;
import com.mitocode.library.application.rs.dto.ClientRecordDto;
import com.mitocode.library.application.rs.dto.OrderBookRecordDto;
import com.mitocode.library.infraestructure.in.messaging.dto.request.CreateOrderBookEventRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.BookResponse;
import com.mitocode.library.infraestructure.in.web.dto.response.ClientResponse;
import com.mitocode.library.infraestructure.in.web.dto.response.OrderBookResponse;

@Configuration
public class MapperConfig {
	@Bean("defaultMapper")
	ModelMapper defaultMapper() {
		return new ModelMapper();
	}

	@Bean("bookMapper")
	ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();

		// Estrategia estricta para que coincidan exactamente los nombres
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		mapper.addConverter(ctx -> {
			BookRecordDto source = ctx.getSource();
			if (source == null)
				return null;
			return new BookResponse(source.id(), source.title(), source.author(), source.isbn(), source.bookStatus());
		}, BookRecordDto.class, BookResponse.class);

		return mapper;
	}

	@Bean("clientMapper")
	ModelMapper clientMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.addConverter(ctx -> {
			ClientRecordDto source = ctx.getSource();
			if (source == null)
				return null;
			return new ClientResponse(source.id(), source.name(), source.surname(), source.dni(), source.age());
		}, ClientRecordDto.class, ClientResponse.class);
		return mapper;
	}

	@Bean("orderBookMapper")
	public ModelMapper orderBookMapper() {
		ModelMapper mapper = new ModelMapper();

		mapper.addConverter(ctx -> {
			OrderBookRecordDto source = ctx.getSource();
			if (source == null)
				return null;

			ClientResponse clientResponse = new ClientResponse(source.client().getId(), source.client().getName(),
					source.client().getSurname(), source.client().getDni(), source.client().getAge());

			List<BookResponse> bookResponses = source.books().stream().map(book -> new BookResponse(book.getId(),
					book.getTitle(), book.getAuthor(), book.getIsbn(), book.getBookStatus())).toList();

			return new OrderBookResponse(source.idOrderBook(), source.dateOrder(), clientResponse, bookResponses,
					source.orderBookStatus());
		}, OrderBookRecordDto.class, OrderBookResponse.class);

		return mapper;
	}

	@Bean("orderBookKafkaMapper")
	public ModelMapper createOrderBookMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		mapper.addConverter(ctx -> {
			CreateOrderBookEventRequest source = ctx.getSource();
			if (source == null)
				return null;

			// Mapear Client
			ClientIdCommand clientCommand = new ClientIdCommand(source.getClient().getId());

			// Mapear Books
			List<BookIdCommand> bookCommands = source.getBooks().stream().map(book -> new BookIdCommand(book.getId()))
					.toList();

			return new CreateOrderBookCommand(clientCommand, bookCommands);
		}, CreateOrderBookEventRequest.class, CreateOrderBookCommand.class);

		return mapper;
	}

}
