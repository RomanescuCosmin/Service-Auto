package com.service.auto.repository;

import com.service.auto.entity.Marca;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MarcaRepository extends BaseRepository<Marca> {


    public List<Marca> findAll() {
        String jpql = "SELECT m FROM Marca m WHERE m.enabled = true ORDER BY m.nume ASC";
        TypedQuery<Marca> query = entityManager.createQuery(jpql, Marca.class);
        return query.getResultList();
    }


}
