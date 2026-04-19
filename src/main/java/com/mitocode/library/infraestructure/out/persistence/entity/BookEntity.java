package com.mitocode.library.infraestructure.out.persistence.entity;

import com.mitocode.library.domain.model.enums.BookStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class BookEntity {
	
	@Id
	@Column(name = "id_book", length = 36)	
	private String id;
	@Column(name = "title", nullable = false, length = 255)
	private String title;
	@Column(name = "author", nullable = false, length = 255)
	private String author;
	@Column(name = "isbn", nullable = false, length = 255)
	private String isbn;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "book_status", nullable = false)
	private BookStatus bookStatus;
	
	@Version
    private Long version; // campo para optimistic locking


}
