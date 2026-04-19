package com.mitocode.library.infraestructure.out.persistence.adapter.jpa;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mitocode.library.domain.model.entity.Book;
import com.mitocode.library.domain.port.out.persistence.BookRepository;
import com.mitocode.library.infraestructure.out.persistence.adapter.CRUDImpl;
import com.mitocode.library.infraestructure.out.persistence.entity.BookEntity;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.BookRepositoryJPA;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.IGenericRepo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//Infraestructura: implementación concreta para Book
public class BookRepositoryJPAAdapter extends CRUDImpl<Book, String, BookEntity> implements BookRepository {

	private final BookRepositoryJPA repo;
	@Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @Override
    protected IGenericRepo<BookEntity, String> getRepo() {
        return repo;
    }

    @Override
    protected ModelMapper getMapper() {
        return mapper;
    }

    @Override
    protected Class<BookEntity> getEntityClass() {
        return BookEntity.class;
    }

    @Override
    protected Class<Book> getDomainClass() {
        return Book.class;
    }

    @Override
    protected void setEntityId(BookEntity entity, String id) {
        entity.setId(id);
    }

    @Override
    public boolean existsByIsbn(String ISBN) {
        return repo.existsByisbn(ISBN);
    }

    @Override
    public List<Book> findByStatusAvailable() {
        return repo.findByBookStatusAvailable().stream()
                   .map(e -> mapper.map(e, Book.class))
                   .toList();
    }
}