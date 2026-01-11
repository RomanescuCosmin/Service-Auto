package com.service.auto.repository;

import com.service.auto.entity.Programare;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProgramareRepository extends BaseRepository<Programare> {

    public List<Object[]> findBookedSlots(LocalDate fromDate) {
        return entityManager.createQuery(
                        "select p.dataProgramare, p.oraProgramare, p.minutProgramare " +
                                "from Programare p " +
                                "where p.canceled = false and p.dataProgramare >= :fromDate",
                        Object[].class)
                .setParameter("fromDate", fromDate)
                .getResultList();
    }
}
