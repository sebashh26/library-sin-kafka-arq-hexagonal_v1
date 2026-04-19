package com.mitocode.library.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookCommand {
	
	private String id;
	private String title;
	private String author;
	private String isbn;

}
