package com.mitocode.library.domain.port.out.persistence;

import java.util.List;
import java.util.Optional;

public interface ICRUD<T, ID>{

	T save(T t) ;
    T update(T t) ;
    List<T> findAll() ;
    Optional<T> findById(ID id);
    void delete(ID id) ;
}
