package com.mitocode.library.infraestructure.in.messaging.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class OrderBookEventResponse {
	
	private boolean success;
    private String message;
    private String correlationId;
    private String topic;
    private LocalDateTime timestamp;


}
