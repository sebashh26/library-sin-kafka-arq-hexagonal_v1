package com.mitocode.library.application.usecase.book;

import org.springframework.stereotype.Component;

import com.mitocode.library.domain.port.out.persistence.OrderBookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OrderBookStateUseCase {
	
	private final OrderBookRepository orderBookRepository;
	
	

}
