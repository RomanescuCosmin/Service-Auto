package com.service.auto.service;

import com.service.auto.dto.FileStorageTypeEnum;
import com.service.auto.entity.FileStorage;
import com.service.auto.security.CustomUserPrincipal;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

@Service
@Transactional
public class FileStorageService extends BaseService {


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
}
