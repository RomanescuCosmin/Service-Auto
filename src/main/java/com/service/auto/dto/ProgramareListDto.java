package com.service.auto.dto;

import java.time.LocalDate;

public record ProgramareListDto(Long id,
                                String nume,
                                String email,

                                LocalDate dataProgramare,
                                Integer oraProgramare,
                                Integer minutProgramare,

                                boolean confirmed,
                                boolean canceled,

                                // MAȘINĂ
                                String marcaNume,
                                String modelNume,

                                // DOCUMENT
                                Long fileStorageId,
                                String fileOriginalName) {

}
