package com.mitocode.library.infraestructure.out.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.mitocode.library.domain.model.enums.OrderBookStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderBookEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer idOrderBook;//a este si quiero generarle un id incremental en la entidad
	private LocalDateTime dateOrder;
	
	@ManyToOne
	@JoinColumn(name="id_client", nullable = false, foreignKey = @ForeignKey(name="FK_OB_CLIENT"))
	private ClientEntity client;
	
	@ManyToMany
    @JoinTable(
        name = "order_book_books",
        joinColumns = @JoinColumn(name = "id_order_book"),//joinColumns es la columna que se va a crear en la tabla intermedia para relacionar con OrderBookEntity
        inverseJoinColumns = @JoinColumn(name = "id_book"))//inverseJoinColumns es la columna que se va a crear en la tabla intermedia para relacionar con BookEntity
    private List<BookEntity> books;
	
    private OrderBookStatus orderBookStatus;

}
