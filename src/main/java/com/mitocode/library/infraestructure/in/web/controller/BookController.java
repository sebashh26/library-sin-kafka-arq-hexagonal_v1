package com.mitocode.library.infraestructure.in.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mitocode.library.infraestructure.in.web.dto.request.book.CreateBookRequest;
import com.mitocode.library.infraestructure.in.web.dto.request.book.UpdateBookRequest;
import com.mitocode.library.infraestructure.in.web.dto.response.BookResponse;
import com.mitocode.library.infraestructure.in.web.service.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Book Controller", description = "Books management endpoints")
public class BookController {
	
	private final BookService bookService;
	
	@PostMapping
    @Operation(summary = "Create a new book", description = "Creates a new book in the system with http protocol")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "409", description = "Book already exists")
    })	
	 public ResponseEntity<BookResponse> create(@Valid @RequestBody CreateBookRequest request){
		System.out.println("Request recibido: " + request);

		BookResponse response  = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
	@GetMapping("/{id}")
    @Operation(summary = "Get book by ID", description = "Retrieves a book by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Book not found")
    })
    public ResponseEntity<BookResponse> findById(@PathVariable("id") String idBook){
        return ResponseEntity.ok(bookService.findByIdBook(idBook));
    }
	
	@GetMapping("/available")
    @Operation(summary = "Get available books", description = "Retrieves available books in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book found"),
            @ApiResponse(responseCode = "404", description = "Books not found")
    })
    public ResponseEntity<List<BookResponse>> findByDni(){
        return ResponseEntity.ok(bookService.findAllAvailableBooks());
    }
	
	@PutMapping("/{id}")
    @Operation(summary = "Update book", description = "Updates the information of a book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book updated successfully"),
            @ApiResponse(responseCode = "404", description = "Book not found"),
            @ApiResponse(responseCode = "400", description = "Book update data")
    })
	public ResponseEntity<BookResponse> update( @PathVariable String id, @Valid @RequestBody UpdateBookRequest request){
		request.setId(id);
		BookResponse response =  bookService.updateBook(request);
        return ResponseEntity.ok(response);
    }
	
	

}
