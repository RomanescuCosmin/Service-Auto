package com.service.auto.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProgramareSlotDto {

    private LocalDate dataProgramare;
    private Integer oraProgramare;
    private Integer minutProgramare;
}
