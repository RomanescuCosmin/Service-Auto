package com.service.auto.mapper;

import com.service.auto.dto.MarcaDto;
import com.service.auto.entity.Marca;

public class MarcaMapper extends BaseMapper {

    public static MarcaDto toDTO(Marca marca) {
        return MarcaDto.builder()
                .id(marca.getId())
                .nume(marca.getNume())
                .taraOrigine(marca.getTaraOrigine())
                .enabled(marca.isEnabled())
                .version(marca.getVersion())
                .build();
    }

    public static Marca toEntity(final MarcaDto dto) {
        return Marca.builder()
                .id(dto.getId())
                .nume(dto.getNume())
                .taraOrigine(dto.getTaraOrigine())
                .enabled(dto.isEnabled())
                .version(dto.getVersion())
                .build();
    }

    public static void setFromDTO(final MarcaDto dto, Marca marca) {
        marca.setId(dto.getId());
        marca.setNume(dto.getNume());
        marca.setTaraOrigine(dto.getTaraOrigine());
        marca.setEnabled(dto.isEnabled());
        marca.setVersion(dto.getVersion());
    }

}
