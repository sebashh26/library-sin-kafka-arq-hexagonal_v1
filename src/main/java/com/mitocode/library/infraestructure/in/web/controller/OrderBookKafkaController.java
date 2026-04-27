package com.mitocode.library.infraestructure.in.web.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.library.infraestructure.in.messaging.dto.request.CreateOrderBookEventRequest;
import com.mitocode.library.infraestructure.in.messaging.dto.response.OrderBookEventResponse;
import com.mitocode.library.infraestructure.in.web.service.OrderBookKafkaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/order-book")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Order Bokk Kafka Messages", description = "API para enviar mensajes directamente una order book to Kafka topics")
public class OrderBookKafkaController {

	private final OrderBookKafkaService orderBookKafkaService;

	@PostMapping("/request")
	@Operation(summary = "Enviar JSON a topic orders", description = "Permite enviar un mensaje JSON directamente al topic orders para testing")
	public ResponseEntity<OrderBookEventResponse> sendToOrderBookTopic(
			@Parameter(description = "JSON message to send to orders topic") @Valid @RequestBody CreateOrderBookEventRequest request) {

		String correlationId = UUID.randomUUID().toString();

		log.info("Received request to send message to orders topic");

		orderBookKafkaService.publishOrderBookCreated(request, correlationId);

		OrderBookEventResponse response = OrderBookEventResponse.builder().success(Boolean.TRUE)
				.message("Solicitud de alquiler enviado a procesamiento").correlationId(correlationId).topic("clientes")
				.timestamp(LocalDateTime.now()).build();
		
		return ResponseEntity.accepted().body(response);

	}

}
