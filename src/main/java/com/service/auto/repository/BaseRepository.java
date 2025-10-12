package com.service.auto.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;

public abstract class BaseRepository<T> implements Serializable {

    @PersistenceContext
    public transient EntityManager entityManager;

    private Class<T> clazz;
    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T merge(T item) {
        T merged = this.entityManager.merge(item);
        this.entityManager.flush();
        return merged;
    }

    public T findById(Long id) {
        if (id == null) return null;
        return entityManager.find(clazz, id);
    }

    public T getReference(Long id) {
        return entityManager.getReference(clazz, id);
    }

}
