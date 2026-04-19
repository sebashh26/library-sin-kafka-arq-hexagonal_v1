package com.mitocode.library.infraestructure.in.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.library.infraestructure.in.web.dto.request.orderbook.CreateOrderBookRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.OrderBookResponse;
import com.mitocode.library.infraestructure.in.web.service.OrderBookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/order-books")
@RequiredArgsConstructor
@Tag(name = "Order Book Controller", description = "Order Books management endpoints")
public class OrderBookController {
	
	private final OrderBookService orderBookService;
	
	@PostMapping
    @Operation(summary = "Create a new order book", description = "Creates a new orde book in the system with http protocol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Book already exists")
    })	
	 public ResponseEntity<OrderBookResponse> create(@Valid @RequestBody CreateOrderBookRequest request){
		OrderBookResponse response  = orderBookService.createOrderBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@GetMapping("/{id}")
    @Operation(summary = "Get order book by ID", description = "Retrieves a order book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Book found"),
            @ApiResponse(responseCode = "404", description = "Order Book not found")
    })
    public ResponseEntity<OrderBookResponse> findById(@PathVariable("id") Integer idOrderBook){
        return ResponseEntity.ok(orderBookService.findByIdOrderBook(idOrderBook));
    }
	
	@GetMapping("/cliente/{id}")
    @Operation(summary = "Get order book by dni", description = "Retrieves a order book by its dni")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order Book found"),
            @ApiResponse(responseCode = "404", description = "Order Book not found")
    })
    public ResponseEntity<OrderBookResponse> findByDniClientOrderBook(@PathVariable("id") String dniClient){
        return ResponseEntity.ok(orderBookService.findByDniClientOrderBook(dniClient));
    }
	

}
