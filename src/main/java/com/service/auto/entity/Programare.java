package com.service.auto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.service.auto.util.DataBaseConstant.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = PROGRAMARE_TABLE)
public class Programare {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "programare_seq")
    @Column(name = PROGRAMARE_ID)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PROGRAMARE_FK_USER, nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PROGRAMARE_FK_FILE_STORAGE)
    private FileStorage fileStorage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PROGRAMARE_FK_MARCA, nullable = false)
    private Marca marca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PROGRAMARE_FK_MODEL, nullable = false)
    private Model model;

    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    @Column(name = PROGRAMARE_DATA, nullable = false)
    private LocalDate dataProgramare;

    @Column(name = PROGRAMARE_ORA)
    private Integer oraProgramare;

    @Column(name = PROGRAMARE_MINUT)
    private Integer minutProgramare;

    @NotNull
    @Column(name = PROGRAMARE_SERIE_SASIU, length = 50)
    private String serieSasiu;

    @Column(name = PROGRAMARE_OBSERVATII, length = 8000)
    private String observatii;

    @NotNull
    @Value(TRUE)
    @Column(name = PROGRAMARE_IS_CONFIRMED)
    private boolean confirmed;

    @NotNull
    @Value(FALSE)
    @Column(name = PROGRAMARE_IS_CANCELED)
    private boolean canceled;

    @Column(name = PROGRAMARE_CREATED_AT)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Version
    @Column(name = PROGRAMARE_VERSION)
    private Integer version;
}
