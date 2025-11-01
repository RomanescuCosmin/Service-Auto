package com.service.auto.dto;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MarcaDto {

    private Long id;
    private String nume;
    private String taraOrigine;
    private boolean enabled;
    private Integer version;

}
