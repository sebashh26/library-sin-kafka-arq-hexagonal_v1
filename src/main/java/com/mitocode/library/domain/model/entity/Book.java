package com.mitocode.library.domain.model.entity;

import java.util.UUID;

import com.mitocode.library.domain.model.enums.BookStatus;
import com.mitocode.library.domain.model.exception.BookInvalidateException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
	
	private String id;
	private String title;
	private String author;
	private String isbn;
	private BookStatus bookStatus;
	private Long version;
	
	public void validateRequiredFields() {
//		if (id == null) {//no es necesario validar el id porque se genera automaticamente en el momento de crear el libro
//			throw new BookInvalidateException("ID is required");
//		}
		if (title == null) {
			throw new BookInvalidateException("Title is required");
		}
		if (author == null) {
			throw new BookInvalidateException("Author is required");
		}
		if (isbn == null) {
			throw new BookInvalidateException("ISBN is required");
		}
//		if (bookStatus == null) {
//			throw new BookInvalidateException("BookStatus is required");
//		}
	}
	
	public void validateAvailable() {
		if (this.bookStatus != BookStatus.AVAILABLE) {
			throw new BookInvalidateException("Book is not available");
		}
	}
	
	public void borrowBook() {
		//this.validateAvailable();
		this.bookStatus = BookStatus.BORROWED;
	}
	
	public void activeAvailableBook() {
		this.bookStatus = BookStatus.AVAILABLE;
	}
	
	public boolean isBorrowed() {
        return this.bookStatus == BookStatus.BORROWED;
    }

	public void update(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }


	public void generateBookId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
