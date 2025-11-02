package com.service.auto.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

import static com.service.auto.util.DataBaseConstant.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = FILE_STORAGE_TABLE)
@SequenceGenerator(name = "file_storage_seq", sequenceName = "file_storage_seq", allocationSize = 1)
public class FileStorage implements BaseEntity {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_storage_seq")
    @Column(name = FILE_STORAGE_ID)
    private Long id;

    @Column(name = FILE_STORAGE_FILE_MD5, length = 100)
    private String fileMd5;

    @NotNull
    @DateTimeFormat(pattern = DATE_PATTERN)
    @Column(name = FILE_STORAGE_UPLOAD_DATE, nullable = false)
    private LocalDate fileUploadDate;

    @Size(max = 500)
    @Column(name = FILE_STORAGE_SUBDIR_PATH)
    private String fileSubdirPath;

    @NotNull
    @Size(max = 500)
    @Column(name = FILE_STORAGE_ROOT_PATH, nullable = false)
    private String fileRootPath;

    @NotNull
    @Size(max = 200)
    @Column(name = FILE_STORAGE_ORIGINAL_NAME, nullable = false)
    private String fileOriginalName;

    @Size(max = 200)
    @Column(name = FILE_STORAGE_UTILIZATOR)
    private String utilizator;

    @NotNull
    @Value(TRUE)
    @Column(name = FILE_STORAGE_IS_ENABLED)
    private boolean enabled;

    @Version
    @Column(name = FILE_STORAGE_VERSION)
    private Integer version;
}
