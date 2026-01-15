package com.service.auto.controller;

import com.service.auto.dto.FileStorageTypeEnum;
import com.service.auto.dto.ProgramareDto;
import com.service.auto.entity.FileStorage;
import com.service.auto.entity.Programare;
import com.service.auto.entity.User;
import com.service.auto.exception.InvalidInputException;
import com.service.auto.security.CustomUserPrincipal;
import groovy.util.logging.Slf4j;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
public class ProgramareController extends  BaseController {


    private static final Logger logger = LoggerFactory.getLogger(ProgramareController.class);

    @GetMapping(value = "/programare", produces = "text/html")
    public String programare (Model uiModel) {
        logger.info("programare page");

        ProgramareDto programareDto = new ProgramareDto();
        uiModel.addAttribute("programareDto", programareDto);
        uiModel.addAttribute("marci", marcaService.findAll());
        uiModel.addAttribute("bookedSlots", programareService.getBookedSlots(LocalDate.now()));

        uiModel.addAttribute("pageMode", "programareDto");

        return "programare";
    }

    @PostMapping(value = "/programare", produces = "text/html")
    public String add (ProgramareDto programareDto,
                       Model uiModel, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, HttpServletRequest request) {

        logger.info("post create Programare cu parametrii : ", programareDto);

        try {

            if (bindingResult.hasErrors()) {
                logger.info("Nu s-a putut adauga inregistrarea", bindingResult.getAllErrors());
                throw new InvalidInputException("Parametrii invalizi");
            }

            uiModel.asMap().clear();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

            MultipartFile file = ((StandardMultipartHttpServletRequest) request).getFile("fisier");
            FileStorage fileStorage = fileStorageService.create(file, FileStorageTypeEnum.PROGRAMARE_AUTO ,principal);

            programareService.create(programareDto, principal.getId(), fileStorage);

            redirectAttributes.addFlashAttribute("saveResult", "ok-add");
            redirectAttributes.addFlashAttribute("succesMessage", "Programarea a fost efectuata cu succes, o sa primiti un mail cu informatiile programarii!");
            return "redirect:/programare";
        } catch (Exception e) {
            logger.error("Eroare la adaugarea inregistrarii", e);
            redirectAttributes.addFlashAttribute("saveResult", "failed-add");
            return "redirect:/programare";
        }
    }


    @GetMapping(value = "/programari-personale", produces = "text/html")
    public String getProgramarePersonala(Model model, Authentication authentication) {
        logger.info("programare personala page");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

        // TODO: Folosește parametrul `authentication` în loc de SecurityContextHolder sau elimină-l dacă nu este necesar.
        // TODO: Dacă ai nevoie de detalii pentru o singură programare, adaugă un endpoint separat cu PathVariable și validări.
        List<Programare> programareList = programareService.findProgramareByUserId(principal.getId());
        model.addAttribute("programareList", programareList);

        return "programari-personale";
    }

}
