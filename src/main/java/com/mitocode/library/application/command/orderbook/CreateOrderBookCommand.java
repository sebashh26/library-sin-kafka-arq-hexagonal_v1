package com.mitocode.library.application.command.orderbook;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderBookCommand {
	
	//private LocalDateTime dateOrder;
	private ClientIdCommand client;
    private List<BookIdCommand> books;
    //private OrderBookStatus orderBookStatus;

}
