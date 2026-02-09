package com.service.auto.repository;

import com.service.auto.dto.ProgramareListDto;
import com.service.auto.dto.ProgramareSlotDto;
import com.service.auto.entity.Programare;
import com.service.auto.filter.ProgramareFilter;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ProgramareRepository extends BaseRepository<Programare> {

    private static final List<String> ALLOWED_SORTS =
            List.of("dataProgramare", "oraProgramare", "createdAt");

    private static final String USER_ID = "userId";
    private static final String FILE_STORAGE_ID = "fileStorageId";
    private static final String CONFIRMED = "confirmed";
    private static final String CANCELED = "canceled";


    public List<ProgramareListDto> find(ProgramareFilter filter, int page, int size) {

        StringBuilder jpql = new StringBuilder(
                "select new com.service.auto.dto.ProgramareListDto(" +
                        " p.id, p.nume, p.email, " +
                        " p.dataProgramare," +
                        " p.oraProgramare," +
                        " p.minutProgramare, " +
                        " p.confirmed," +
                        " p.canceled, " +
                        " m.nume," +
                        " mo.numeModel, " +
                        " fs.id, " +
                        " fs.fileOriginalName" +
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
            query.setParameter(USER_ID, filter.userId());
        }

        if (filter.confirmed() != null) {
            query.setParameter(CONFIRMED, filter.confirmed());
        }

        if (filter.canceled() != null) {
            query.setParameter(CANCELED, filter.canceled());
        }

        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        return query.getResultList();
    }


    public long count(ProgramareFilter filter) {
        StringBuilder jpql = new StringBuilder(" select count(p.id) from Programare p where 1=1 ");

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
            query.setParameter(USER_ID, filter.userId());
        }

        if (filter.confirmed() != null) {
            query.setParameter(CONFIRMED, filter.confirmed());
        }

        if (filter.canceled() != null) {
            query.setParameter(CANCELED, filter.canceled());
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
        TypedQuery<Programare> query = entityManager.createQuery(
                        "select p from Programare p where p.user.id = :userId order by p.dataProgramare desc, p.oraProgramare desc, p.minutProgramare desc",
                        Programare.class)
                .setParameter(USER_ID, userId);

        return query.getResultList();
    }

    public Programare getProgramareByFileStorageId(Long fileStorageId) {
        TypedQuery<Programare> query = entityManager.createQuery(
                        "select p from Programare p where p.fileStorage.id = :fileStorageId",
                        Programare.class)
                .setParameter(FILE_STORAGE_ID, fileStorageId);

        return query.getSingleResult();
    }

    public Programare findProgramareByIdAndUser(Long programareId, Long userId) {
        TypedQuery<Programare> query = entityManager.createQuery(
                        "select p from Programare p where p.id = :programareId and p.user.id = :userId",
                        Programare.class)
                .setParameter("programareId", programareId)
                .setParameter(USER_ID, userId);

        return query.getSingleResult();
    }
}
