package com.service.auto.controller;


import com.service.auto.dto.ProgramareListDto;
import com.service.auto.entity.Programare;
import com.service.auto.filter.PageResult;
import com.service.auto.filter.ProgramareFilter;
import com.service.auto.security.CustomUserPrincipal;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import static com.service.auto.util.DataBaseConstant.DATE_PATTERN;


@Controller
@RequestMapping("/programare")
public class ProgramariPersonaleController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProgramareController.class);

    @GetMapping(value = "/programari-personale", produces = "text/html")
    public String programariPersonaleList(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "dataProgramare") String sort,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(required = false) Boolean confirmed,
            @RequestParam(required = false) Boolean canceled,
            @RequestParam(required = false) String search,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = DATE_PATTERN)LocalDate data,
            Model model) {
        logger.info("programare personala page");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        String normalizedSearch = StringUtils.isBlank(search) ? null : search.trim();
        ProgramareFilter filter = new ProgramareFilter(principal.getId(), sort, order, confirmed, canceled, data, normalizedSearch);

        PageResult<ProgramareListDto> programareList = programareService.list(filter, page, size);

        model.addAttribute("programareList", programareList.content());

        model.addAttribute("page", programareList);
        model.addAttribute("filter", filter);

        return "programari-personale";
    }


    @PostMapping(value = "/programari-personale/anulare-programare/{id}", produces = "text/html")
    public String anulareProgramare(HttpServletRequest httpServletRequest, @PathVariable("id") Long programareId,
                                    Model uiModel,
                                    RedirectAttributes redirAttrs) {

        logger.info("anulareProgramare with parameters: programareId = {}", programareId);

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

            programareService.setAnulareProgramare(programareId, principal.getId());

            uiModel.asMap().clear();

            redirAttrs.addFlashAttribute("saveResult", "ok-add");
            redirAttrs.addFlashAttribute("succesMessage", "Programarea a fost anulată!");
            return "redirect:/programari-personale";
        } catch (Exception ex) {
            logger.error("--------------------failed to anulareProgramare: ", ex);
            return  "redirect:/programari-personale";
        }
    }


    @GetMapping("/files/{fileStorageId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileStorageId") Long fileStorageId) {
        logger.info("downloadFileStorageById for programare page with fileStorageId={}", fileStorageId);

        if (fileStorageId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fileStorageId is required");
        }

        Programare programare = programareService.getProgramareByFileStorageId(fileStorageId);

        if (programare == null || programare.getFileStorage() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "document not found");
        }

        String storageKey = programare.getFileStorage().getFileMd5();

        if (StringUtils.isBlank(storageKey)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found");
        }

        String originalName = programare.getFileStorage().getFileOriginalName();

        if (StringUtils.isBlank(originalName)) {
            originalName = "document";
        }

        String rootStr = environment.getProperty("file.storage.path");
        String subdirStr = environment.getProperty("file.storage.subdir.programare_auto");

        if (StringUtils.isBlank(rootStr) || StringUtils.isBlank(subdirStr)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Storage is not configured");
        }

        Path root = Paths.get(rootStr).toAbsolutePath().normalize();
        Path filePath = root.resolve(subdirStr).resolve(storageKey).normalize();

        if (!filePath.startsWith(root)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
        }

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath) || !Files.isReadable(filePath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        Resource resource = toResource(filePath);

        String contentType = probeContentType(filePath);

        if (StringUtils.isBlank(contentType)) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        long size = safeSize(filePath);

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(originalName, StandardCharsets.UTF_8).build();

        ResponseEntity.BodyBuilder builder = ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .cacheControl(CacheControl.noStore());

        if (size >= 0) {
            builder.contentLength(size);
        }
        return builder.body(resource);
    }

    private long safeSize(Path filePath) {
        try {
            return Files.size(filePath);
        } catch (Exception e) {
            return -1l;
        }

    }

    private Resource toResource (Path filePath) {
        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid file URI");
        }
    }

    private String probeContentType (Path filePath) {
        try {
            return Files.probeContentType(filePath);
        } catch (IOException e) {
            return null;
        }
    }
}
