package com.mitocode.library.infraestructure.in.web.exception;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mitocode.library.application.exception.BookAlreadyBorrowedException;
import com.mitocode.library.application.exception.BookNotFoundException;
import com.mitocode.library.application.exception.InvalidRequestException;
import com.mitocode.library.domain.model.exception.BookInvalidateException;
import com.mitocode.library.domain.model.exception.ClientInvalidateException;
import com.mitocode.library.domain.model.exception.OrderBookInvalidateException;
import com.mitocode.library.infraestructure.out.persistence.exception.ModelNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionMapper {

	@ExceptionHandler({ ClientInvalidateException.class, BookInvalidateException.class,
			OrderBookInvalidateException.class, ModelNotFoundException.class, InvalidRequestException.class,
			BookAlreadyBorrowedException.class, BookNotFoundException.class })
	public ResponseEntity<ErrorResponse> handleInvalidRequestException(RuntimeException ex,
			HttpServletRequest request) {
		ErrorResponse errorResponse = new ErrorResponse("01", "Datos inválidos", ex.getMessage());

		return ResponseEntity.badRequest().body(errorResponse);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
		String details = ex.getBindingResult().getFieldErrors().stream()
				.map(e -> e.getField() + " : " + e.getDefaultMessage()).collect(Collectors.joining(", "));

		ErrorResponse errorResponse = new ErrorResponse("02", "Error de validación", details.toString());
		return ResponseEntity.badRequest().body(errorResponse);
	}

}
