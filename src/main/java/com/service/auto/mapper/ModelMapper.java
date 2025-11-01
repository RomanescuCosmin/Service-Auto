package com.service.auto.mapper;

import com.service.auto.dto.ModelAutoDto;
import com.service.auto.entity.ModelAuto;

public class ModelMapper extends BaseMapper {

    private ModelMapper() {}

    public static ModelAutoDto toDTO(ModelAuto model) {
        return ModelAutoDto.builder()
                .id(model.getId())
                .marcaId(model.getMarca() != null ? model.getMarca().getId() : null)
                .numeModel(model.getNumeModel())
                .anFabricatie(model.getAnFabricatie())
                .enabled(model.isEnabled())
                .version(model.getVersion())
                .build();
    }

    public static ModelAuto toEntity(final ModelAutoDto dto) {
        return ModelAuto.builder()
                .id(dto.getId())
                .marca(marcaRepository.findById(dto.getMarcaId()))
                .numeModel(dto.getNumeModel())
                .anFabricatie(dto.getAnFabricatie())
                .enabled(dto.isEnabled())
                .version(dto.getVersion())
                .build();
    }

    public static void setFromDTO(final ModelAutoDto modelAutoDto, ModelAuto modelAuto) {
        modelAuto.setId(modelAutoDto.getId());
        modelAuto.setMarca(marcaRepository.findById(modelAutoDto.getMarcaId()));
        modelAuto.setNumeModel(modelAutoDto.getNumeModel());
        modelAuto.setAnFabricatie(modelAutoDto.getAnFabricatie());
        modelAuto.setEnabled(modelAutoDto.isEnabled());
        modelAuto.setVersion(modelAutoDto.getVersion());
    }
}
