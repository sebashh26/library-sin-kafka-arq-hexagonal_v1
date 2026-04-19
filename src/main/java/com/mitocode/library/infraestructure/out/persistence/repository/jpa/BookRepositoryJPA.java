package com.mitocode.library.infraestructure.out.persistence.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mitocode.library.infraestructure.out.persistence.entity.BookEntity;



@Repository
public interface BookRepositoryJPA extends IGenericRepo<BookEntity, String> {
	
	boolean existsByisbn(String isbn);
	
	@Query("SELECT b FROM BookEntity b WHERE b.bookStatus = com.mitocode.library.domain.model.enums.BookStatus.AVAILABLE")
	List<BookEntity> findByBookStatusAvailable();

}
