package com.mitocode.library.domain.port.out.persistence;

import java.util.List;

import com.mitocode.library.domain.model.entity.Book;

public interface BookRepository extends ICRUD<Book, String> {
	
	boolean existsByIsbn(String ISBN);
	List<Book> findByStatusAvailable();

}
