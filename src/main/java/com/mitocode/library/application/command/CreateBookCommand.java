package com.mitocode.library.application.command;

import com.mitocode.library.domain.model.enums.BookStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookCommand {
	
	private String name;
	private String author;
	private String isbn;
	private String title;
	private BookStatus bookStatus;
}
