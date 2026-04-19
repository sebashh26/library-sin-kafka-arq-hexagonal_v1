package com.mitocode.library.infraestructure.in.web.dto.request.book;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateBookRequest {
	
	@NotEmpty(message = "Title is required")
	private String title;
	@NotEmpty(message = "Author is required")
	private String author;
	@NotEmpty(message = "ISBN is required")
	private String isbn;
	//@NotEmpty(message = "BookStatus is required")
	//private BookStatus bookStatus;

}
