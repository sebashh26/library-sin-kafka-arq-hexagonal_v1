package com.mitocode.library.infraestructure.out.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;

import com.mitocode.library.infraestructure.out.persistence.exception.ModelNotFoundException;
import com.mitocode.library.infraestructure.out.persistence.repository.jpa.IGenericRepo;

//Infraestructura: implementación genérica
public abstract class CRUDImpl<T, ID, E> {

    protected abstract IGenericRepo<E, ID> getRepo();
    protected abstract ModelMapper getMapper();
    protected abstract Class<E> getEntityClass();
    protected abstract Class<T> getDomainClass();
    protected abstract void setEntityId(E entity, ID id);


    public T save(T domain)  {
        E entity = getMapper().map(domain, getEntityClass());
        E saved = getRepo().save(entity);
        return getMapper().map(saved, getDomainClass());
    }

    public T update( T domain)  {
    	E entity = getMapper().map(domain, getEntityClass());
        E updated = getRepo().save(entity);
        return getMapper().map(updated, getDomainClass());

    }

    public List<T> findAll()  {
        return getRepo().findAll().stream()
                .map(e -> getMapper().map(e, getDomainClass()))
                .toList();
    }

    public Optional<T> findById(ID id) {
        return getRepo().findById(id)
                .map(e -> getMapper().map(e, getDomainClass()));
    }

    public void delete(ID id)  {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        getRepo().deleteById(id);
    }

}