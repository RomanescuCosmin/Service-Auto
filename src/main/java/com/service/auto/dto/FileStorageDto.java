package com.service.auto.dto;

import lombok.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileStorageDto {

    private Long id;
    private String fileMd5;
    private LocalDate fileUploadDate;
    private String fileSubdirPath;
    private String fileRootPath;
    private String fileOriginalName;
    private String utilizator;
    private boolean enabled;
    private Integer version;
}
