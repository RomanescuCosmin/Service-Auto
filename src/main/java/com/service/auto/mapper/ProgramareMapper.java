package com.service.auto.mapper;

import com.service.auto.dto.ProgramareDto;
import com.service.auto.entity.FileStorage;
import com.service.auto.entity.Programare;

import java.time.LocalDateTime;

public class ProgramareMapper extends BaseMapper {

    public static ProgramareDto toDTO(final Programare programare) {
        return ProgramareDto.builder()
                .id(programare.getId())
                .userId(programare.getUser() != null ? programare.getUser().getId() : null)
                .fileStorageId(programare.getFileStorage() != null ? programare.getFileStorage().getId() : null)
                .marcaId(programare.getMarca() != null ? programare.getMarca().getId() : null)
                .modelId(programare.getModelAuto() != null ? programare.getModelAuto().getId() : null)
                .modelNume(programare.getModelAuto() != null ? programare.getModelAuto().getNumeModel() : null)
                .nume(programare.getNume())
                .telefon(programare.getTelefon())
                .email(programare.getEmail())
                .dataProgramare(programare.getDataProgramare())
                .oraProgramare(programare.getOraProgramare())
                .minutProgramare(programare.getMinutProgramare())
                .serieSasiu(programare.getSerieSasiu())
                .observatii(programare.getObservatii())
                .confirmed(programare.isConfirmed())
                .canceled(programare.isCanceled())
                .createdAt(programare.getCreatedAt())
                .version(programare.getVersion())
                .build();
    }

    public static Programare toEntity(final ProgramareDto dto, Long userId, FileStorage fileStorage) {
        return Programare.builder()
                .id(dto.getId())
                .user(userRepository.findById(userId))
                .fileStorage(fileStorage)
                .marca(marcaRepository.findById(dto.getMarcaId()))
                .modelAuto(modelAutoRepository.findById(dto.getModelId()))
                .nume(dto.getNume())
                .telefon(dto.getTelefon())
                .email(dto.getEmail())
                .dataProgramare(dto.getDataProgramare())
                .oraProgramare(dto.getOraProgramare())
                .minutProgramare(dto.getMinutProgramare())
                .serieSasiu(dto.getSerieSasiu())
                .observatii(dto.getObservatii())
                .createdAt(LocalDateTime.now())
                .version(dto.getVersion())
                .build();
    }

    public static void setFromDTO(final ProgramareDto dto, Programare programare) {
        programare.setId(dto.getId());
        programare.setUser(userRepository.findById(dto.getUserId()));
        programare.setFileStorage(fileStorageRepository.findById(dto.getFileStorageId()));
        programare.setMarca(marcaRepository.findById(dto.getMarcaId()));
        programare.setModelAuto(modelAutoRepository.findById(dto.getModelId()));
        programare.setNume(dto.getNume());
        programare.setTelefon(dto.getTelefon());
        programare.setEmail(dto.getEmail());
        programare.setDataProgramare(dto.getDataProgramare());
        programare.setOraProgramare(dto.getOraProgramare());
        programare.setMinutProgramare(dto.getMinutProgramare());
        programare.setSerieSasiu(dto.getSerieSasiu());
        programare.setObservatii(dto.getObservatii());
        programare.setConfirmed(dto.isConfirmed());
        programare.setCanceled(dto.isCanceled());
        programare.setCreatedAt(dto.getCreatedAt());
        programare.setVersion(dto.getVersion());
    }
}