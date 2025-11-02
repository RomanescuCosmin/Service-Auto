package com.service.auto.controller;

import com.service.auto.dto.ModelAutoSimplifyDto;
import com.service.auto.dto.ProgramareDto;
import com.service.auto.exception.InvalidInputException;
import com.service.auto.security.CustomUserPrincipal;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/programare")
public class ProgramareController extends  BaseController {


    private static final Logger logger = LoggerFactory.getLogger(ProgramareController.class);

    @GetMapping(value = "/list", produces = "text/html")
    public String programare (Model uiModel) {
        logger.info("programare page");

        ProgramareDto programareDto = new ProgramareDto();
        uiModel.addAttribute("programareDto", programareDto);
        uiModel.addAttribute("marci", marcaService.findAll());

        uiModel.addAttribute("pageMode", "programareDto");

        return "programare";
    }

    @PostMapping(value = "/list", produces = "text/html")
    public String add (ProgramareDto programareDto,
                       Model uiModel, BindingResult bindingResult,RedirectAttributes redirectAttributes) {

        logger.info("post create Programare cu parametrii : ", programareDto);

        try {

            if (bindingResult.hasErrors()) {
                logger.info("Nu s-a putut adauga inregistrarea", bindingResult.getAllErrors());
                throw new InvalidInputException("Parametrii invalizi");
            }

            uiModel.asMap().clear();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

            programareService.create(programareDto, principal.getId());

            redirectAttributes.addFlashAttribute("saveResult", "ok-add");
            redirectAttributes.addFlashAttribute("succesMessage", "Programarea a fost efectuata cu succes, o sa primiti un mail cu informatiile programarii!");
            return "redirect:/programare/list";
        } catch (Exception e) {
            logger.error("Eroare la adaugarea inregistrarii", e);
            redirectAttributes.addFlashAttribute("saveResult", "failed-add");
            return "redirect:/programare/list";
        }
    }

    @GetMapping(value = "/byMarca/{marcaId}", produces = "application/json")
    @ResponseBody
    public List<ModelAutoSimplifyDto> getModelsByMarca(@PathVariable("marcaId") Long marcaId) {
        logger.info("Cerere GET pentru modelele mărcii cu ID {}", marcaId);
        return modelAutoService.findModelsByMarca(marcaId);
    }

}
