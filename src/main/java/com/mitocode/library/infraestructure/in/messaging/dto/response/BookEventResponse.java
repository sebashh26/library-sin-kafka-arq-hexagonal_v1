package com.mitocode.library.infraestructure.in.messaging.dto.response;

import com.mitocode.library.domain.model.enums.BookStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookEventResponse {
	
	private String id;
	private String title;
	private String author;
	private String isbn;
	private BookStatus bookStatus;

}
