package com.service.auto.repository;

import com.service.auto.dto.ProgramareSlotDto;
import com.service.auto.entity.Programare;
import jakarta.persistence.NoResultException;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProgramareRepository extends BaseRepository<Programare> {

    public List<ProgramareSlotDto> findBookedSlots(LocalDate fromDate) {
        return entityManager.createQuery(
                        "select new com.service.auto.dto.ProgramareSlotDto(" +
                                " p.dataProgramare, " +
                                " p.oraProgramare," +
                                " p.minutProgramare) " +
                                " from Programare p " +
                                " where p.canceled = false and p.dataProgramare >= :fromDate",
                        ProgramareSlotDto.class)
                .setParameter("fromDate", fromDate)
                .getResultList();
    }

    public List<Programare> findProgramareByUserId(Long userId) {
        // TODO: Dacă ai nevoie de SQL nativ, folosește createNativeQuery cu sintaxă SQL și mapare corectă.
        return entityManager.createQuery(
                        "select p from Programare p where p.user.id = :userId order by p.dataProgramare desc, p.oraProgramare desc, p.minutProgramare desc",
                        Programare.class)
                .setParameter("userId", userId)
                .getResultList();

    }

    public Programare getProgramareByFileStorageId(Long fileStorageId) {
        try {
            return entityManager.createQuery(
                            "select p from Programare p where p.fileStorage.id = :fileStorageId",
                            Programare.class)
                    .setParameter("fileStorageId", fileStorageId)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    public Programare findProgramareByIdAndUser(Long programareId, Long userId) {
        try {
            return entityManager.createQuery(
                            "select p from Programare p where p.id = :programareId and p.user.id = :userId",
                            Programare.class)
                    .setParameter("programareId", programareId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }

    }
}
