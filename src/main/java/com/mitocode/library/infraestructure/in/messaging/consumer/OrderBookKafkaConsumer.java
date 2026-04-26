package com.mitocode.library.infraestructure.in.messaging.consumer;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.library.application.command.orderbook.CreateOrderBookCommand;
import com.mitocode.library.application.rs.dto.OrderBookRecordDto;
import com.mitocode.library.application.usecase.book.CreateOrderBookUseCase;
import com.mitocode.library.infraestructure.in.messaging.dto.request.CreateOrderBookEventRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderBookKafkaConsumer {

	private static final Logger LOG = LoggerFactory.getLogger(OrderBookKafkaConsumer.class);

	@Qualifier("orderBookKafkaMapper")
	private final ModelMapper mapper;

	private final CreateOrderBookUseCase createOrderBookUseCase;

	@KafkaListener(topics = "${app.topic.orders}", containerFactory = "ordersBookListener")
	@Transactional(transactionManager = "transactionManager")
	public void consume(@Payload CreateOrderBookEventRequest request,
			@Header(KafkaHeaders.RECEIVED_PARTITION) int partition, @Header(KafkaHeaders.OFFSET) int offset,
			Acknowledgment ack) {

		// OrderBookEventResponse response;
		try {
			CreateOrderBookCommand command = mapper.map(request, CreateOrderBookCommand.class);

			OrderBookRecordDto dto = createOrderBookUseCase.execute(command);
			// response = mapper.map(dto, OrderBookEventResponse.class);
			// Aquí podrías enviar la respuesta a otro topic si es necesario con un response
			// producer, o simplemente loguear el resultado

			LOG.info("Order Book created successfully: {}", dto.idOrderBook());
			LOG.info("order received from partition-{} with offset-{}", partition, offset);
			ack.acknowledge();
		} catch (Exception e) {
			log.error("Error processing merchant creation request: {}", e.getMessage(), e.getCause());

			// Mapear error a mensaje Kafka (delegado al mapper)
			//response = merchantKafkaResponseMapper.toErrorResponse(request, e);
		}
	}

}
