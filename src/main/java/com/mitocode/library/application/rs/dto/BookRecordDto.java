package com.mitocode.library.application.rs.dto;

import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.model.enums.BookStatus;

public record BookRecordDto(String id, String title, String author, String isbn, BookStatus bookStatus) {

	public static BookRecordDto fromDomain(Book book) {
        return new BookRecordDto(
            book.getId(),
            book.getTitle(),
            book.getAuthor(),
            book.getIsbn(),
            book.getBookStatus()
        );
    }

}
