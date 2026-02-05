package com.service.auto.repository;

import com.service.auto.dto.ProgramareListDto;
import com.service.auto.dto.ProgramareSlotDto;
import com.service.auto.entity.Programare;
import com.service.auto.filter.ProgramareFilter;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProgramareRepository extends BaseRepository<Programare> {

    private static final List<String> ALLOWED_SORTS =
            List.of("dataProgramare", "oraProgramare", "createdAt");


    public List<ProgramareListDto> find(ProgramareFilter filter, int page, int size) {

        StringBuilder jpql = new StringBuilder(
                "select new com.service.auto.dto.ProgramareListDto(" +
                        " p.id, p.nume, p.email, " +
                        " p.dataProgramare, p.oraProgramare, p.minutProgramare, " +
                        " p.confirmed, p.canceled, " +
                        " m.nume, mo.numeModel, " +
                        " fs.id, fs.fileOriginalName" +
                        ") " +
                        " FROM Programare p " +
                        " LEFT JOIN p.marca m " +
                        " LEFT JOIN p.modelAuto mo " +
                        " LEFT JOIN p.fileStorage fs " +
                        " where 1=1 "
        );


        if (filter.userId() != null) {
            jpql.append(" and p.user.id = :userId");
        }

        if (filter.confirmed() != null) {
            jpql.append(" and p.confirmed = :confirmed");
        }

        if (filter.canceled() != null) {
            jpql.append(" and p.canceled = :canceled");
        }

        if (filter.hasSort() && ALLOWED_SORTS.contains(filter.sort())) {
            jpql.append(" order by p.")
                    .append(filter.sort())
                    .append(" ")
                    .append("desc".equalsIgnoreCase(filter.order()) ? "desc" : "asc");
        } else {
            jpql.append(" order by p.dataProgramare desc, p.oraProgramare desc, p.minutProgramare desc");
        }

        TypedQuery<ProgramareListDto> query = entityManager.createQuery(jpql.toString(), ProgramareListDto.class);

        if (filter.userId() != null) {
            query.setParameter("userId", filter.userId());
        }

        if (filter.confirmed() != null) {
            query.setParameter("confirmed", filter.confirmed());
        }

        if (filter.canceled() != null) {
            query.setParameter("canceled", filter.canceled());
        }

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }


    public long count(ProgramareFilter filter) {
        StringBuilder jpql = new StringBuilder(
                " select count(p.id) from Programare p where 1=1 "
        );

        if (filter.userId() != null) {
            jpql.append(" and p.user.id = :userId");
        }

        if (filter.confirmed() != null) {
            jpql.append(" and p.confirmed = :confirmed");
        }

        if (filter.canceled() != null) {
            jpql.append(" and p.canceled = :canceled");
        }

        TypedQuery<Long> query = entityManager.createQuery(jpql.toString(), Long.class);
        if (filter.userId() != null) {
            query.setParameter("userId", filter.userId());
        }

        if (filter.confirmed() != null) {
            query.setParameter("confirmed", filter.confirmed());
        }

        if (filter.canceled() != null) {
            query.setParameter("canceled", filter.canceled());
        }
        return query.getSingleResult();
    }

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
