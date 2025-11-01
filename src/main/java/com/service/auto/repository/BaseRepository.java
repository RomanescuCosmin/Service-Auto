package com.service.auto.repository;

import com.service.auto.entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public abstract class BaseRepository<T extends BaseEntity> implements Serializable {

    @PersistenceContext
    public transient EntityManager entityManager;

    private final Class<T> clazz;

    @SuppressWarnings("unchecked")
    public BaseRepository() {
        this.clazz = (Class<T>) ((ParameterizedType)
                getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public T merge(T item) {
        T merged = this.entityManager.merge(item);
        this.entityManager.flush();
        return merged;
    }

    public T findById(Long id) {
        if (id == null) {
            return null;
        }
        return entityManager.find(clazz, id);
    }

    public void deleteById(Long id) {
        T entity = findById(id);
        if (entity != null) entityManager.remove(entity);
    }

    public Class<T> getClazz() {
        return clazz;
    }
}
