package com.mitocode.library.application.usecase.book;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.application.query.GetDataByDNIQuery;
import com.mitocode.library.application.rs.dto.OrderBookRecordDto;
import com.mitocode.library.domain.model.exception.OrderBookInvalidateException;
import com.mitocode.library.domain.port.out.persistence.OrderBookRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GetOrderByDniClientUseCase {
	
	private final OrderBookRepository orderBookRepository;
	
	@Qualifier("defaultMapper")
	private final ModelMapper mapper;
	
	public OrderBookRecordDto execute(GetDataByDNIQuery getOrderByDNIQuery) {
		return orderBookRepository.findByClientDni(getOrderByDNIQuery.dni())
				.map(OrderBookRecordDto::fromDomain)
				.orElseThrow(() -> new OrderBookInvalidateException("No orders found for client with DNI: " + getOrderByDNIQuery.dni()));
	}

}
