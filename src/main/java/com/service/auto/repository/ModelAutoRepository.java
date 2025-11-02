package com.service.auto.repository;

import com.service.auto.entity.ModelAuto;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelAutoRepository extends BaseRepository<ModelAuto> {

    public List<ModelAuto> findByMarcaId(Long marcaId) {
        String jpql = "SELECT m FROM ModelAuto m WHERE m.marca.id = :marcaId AND m.enabled = true";
        TypedQuery<ModelAuto> query = entityManager.createQuery(jpql, ModelAuto.class);
        query.setParameter("marcaId", marcaId);
        return query.getResultList();
    }

}
