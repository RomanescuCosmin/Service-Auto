package com.service.auto.service;

import com.service.auto.dto.FileStorageTypeEnum;
import com.service.auto.entity.FileStorage;
import com.service.auto.security.CustomUserPrincipal;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class FileStorageService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    public FileStorage create(MultipartFile file, FileStorageTypeEnum fileStorageTypeEnum, CustomUserPrincipal principal) {

        try {
            byte[] bytes = file.getBytes();
            // create document on disk
            // 2. Calculam md5 pe bytes
            String fileMd5 = DigestUtils.md5DigestAsHex(bytes);

            // 3. Citim calea din properties
            String fileRoot = environment.getProperty("file.storage.path");
            String filePath = environment.getProperty(fileStorageTypeEnum.path);

            // 4. Construim path ul final: root + subfolder + md5
            Path targetFile = Paths.get(fileRoot, filePath, fileMd5);

            // 5. Cream directoarele daca nu exista
            Files.createDirectories(targetFile.getParent());

            // 6. scriem fisierul pe disc
            Files.write(targetFile, bytes, StandardOpenOption.CREATE);

            // 7. Contruim inregistrarea pentru salvarea in db
            FileStorage fileStorage = new FileStorage();

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String baseName = FilenameUtils.getBaseName(file.getOriginalFilename());

            fileStorage.setFileMd5(fileMd5);
            fileStorage.setFileOriginalName(baseName + "." + extension);
            fileStorage.setFileRootPath(fileRoot);
            fileStorage.setFileSubdirPath(filePath);
            fileStorage.setFileUploadDate(LocalDate.now());
            fileStorage.setUtilizator(principal.getUsername() + " email: " + principal.getEmail());

            return fileStorageRepository.merge(fileStorage);

        } catch (Exception exception) {
            throw new ServiceException(exception.getMessage());
        }

    }

    public Optional<byte[]> loadFileBytes(FileStorage fileStorage) {
        if (fileStorage == null) {
            return Optional.empty();
        }

        Path targetFile = Paths.get(fileStorage.getFileRootPath(), fileStorage.getFileSubdirPath(), fileStorage.getFileMd5());
        if (!Files.exists(targetFile)) {
            return Optional.empty();
        }

        try {
            return Optional.of(Files.readAllBytes(targetFile));
        } catch (IOException exception) {
            logger.warn("Nu am putut citi fișierul pentru FileStorage id {}.", fileStorage.getId(), exception);
            return Optional.empty();
        }
    }
}
