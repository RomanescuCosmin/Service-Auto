package com.service.auto.dto;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModelAutoDto {

    private Long id;
    private Long marcaId;
    private String numeModel;
    private Integer anFabricatie;
    private boolean enabled;
    private Integer version;
}
