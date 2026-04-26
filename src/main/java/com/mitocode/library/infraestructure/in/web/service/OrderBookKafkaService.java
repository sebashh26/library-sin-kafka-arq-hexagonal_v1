package com.mitocode.library.infraestructure.in.web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.library.infraestructure.in.messaging.dto.request.CreateOrderBookEventRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderBookKafkaService {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Value("${app.topic.orders}")
	private String ordersTopic;
	
	@Transactional("kafkaTransactionManager")
	public void publishOrderBookCreated(CreateOrderBookEventRequest request, String correlationId) {
        // Enviamos el DTO junto con correlationId para trazabilidad
		request.setCorrelationId(correlationId);
        kafkaTemplate.send(ordersTopic, request.getKey(), request);
    }


}
