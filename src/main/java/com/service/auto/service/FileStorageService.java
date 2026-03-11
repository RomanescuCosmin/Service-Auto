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

import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Set;

@Service
@Transactional
public class FileStorageService extends BaseService {

    private final Logger logger = LoggerFactory.getLogger(FileStorageService.class);
    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("pdf", "doc", "docx", "jpg", "png");

    public FileStorage create(MultipartFile multipartFile, FileStorageTypeEnum fileStorageTypeEnum, CustomUserPrincipal principal) {


        try (InputStream inputStream = multipartFile.getInputStream()){
            // create document on disk
            // 2. Calculam md5 pe bytes
            String fileMd5 = DigestUtils.md5DigestAsHex(multipartFile.getInputStream());

            // 3. Citim calea din properties
            String fileRoot = environment.getProperty("file.storage.path");
            String filePath = environment.getProperty(fileStorageTypeEnum.path);

            // 4. Construim path ul final: root + subfolder + md5
            Path targetFile = Paths.get(fileRoot, filePath, fileMd5);

            // 5. Cream directoarele daca nu exista
            Files.createDirectories(targetFile.getParent());

            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String baseName = FilenameUtils.getBaseName(multipartFile.getOriginalFilename());

            if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
                throw  new ServiceException("Tip de fisier nepermis: " + extension);
            }

            // 6. scriem fisierul pe disc
            Files.copy(inputStream, targetFile, StandardCopyOption.REPLACE_EXISTING);

            // 7. Contruim inregistrarea pentru salvarea in db
            FileStorage fileStorage = new FileStorage();

            fileStorage.setFileMd5(fileMd5);
            fileStorage.setFileOriginalName(baseName + "." + extension);
            fileStorage.setFileRootPath(fileRoot);
            fileStorage.setFileSubdirPath(filePath);
            fileStorage.setFileUploadDate(LocalDate.now());
            fileStorage.setUtilizator(principal.getUsername() + " email: " + principal.getEmail());

            return fileStorageRepository.merge(fileStorage);

        } catch (Exception exception) {
            logger.error("Eroare la salvarea fisierului pe disc: ", exception);
            throw new ServiceException(exception.getMessage());
        }

    }
}
