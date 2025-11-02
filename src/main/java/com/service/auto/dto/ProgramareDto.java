package com.service.auto.dto;

import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProgramareDto {

    private Long id;
    private Long userId;
    private Long fileStorageId;
    private Long marcaId;
    private Long modelId;
    private String modelNume;

    private String nume;
    private int telefon;
    private String email;

    private LocalDate dataProgramare;
    private Integer oraProgramare;
    private Integer minutProgramare;
    private String serieSasiu;
    private String observatii;

    private boolean confirmed;
    private boolean canceled;
    private LocalDateTime createdAt;
    private Integer version;
}
