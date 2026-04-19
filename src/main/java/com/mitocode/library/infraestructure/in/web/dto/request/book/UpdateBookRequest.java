package com.mitocode.library.infraestructure.in.web.dto.request.book;

import com.mitocode.library.domain.model.enums.BookStatus;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateBookRequest {
	
	private String id;
	
	@NotEmpty(message = "Title is required")
	private String title;
	@NotEmpty(message = "Author is required")
	private String author;
	@NotEmpty(message = "ISBN is required")
	private String isbn;
	@NotNull(message = "BookStatus is required")	
	private BookStatus bookStatus;

}
