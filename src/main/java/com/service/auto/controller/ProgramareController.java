package com.service.auto.controller;

import com.service.auto.dto.ProgramareDto;
import com.service.auto.exception.InvalidInputException;
import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class ProgramareController extends  BaseController {


    private static final Logger logger = LoggerFactory.getLogger(ProgramareController.class);

    @GetMapping(value = "/programare", produces = "text/html")
    public String programare (Model uiModel) {
        logger.info("programare page");

        ProgramareDto programareDto = new ProgramareDto();
        uiModel.addAttribute("programareDto", programareDto);
        uiModel.addAttribute("pageMode", "programareDto");

        return "programare";
    }

    @PostMapping(value = "/programare", produces = "text/html")
    public String addProgramare (@PathVariable(name = "programareDto") ProgramareDto programareDto,
                       Model uiModel, BindingResult bindingResult,RedirectAttributes redirectAttributes) {

        logger.info("post create Programare cu parametrii : ", programareDto);

        try {

            if (bindingResult.hasErrors()) {
                logger.info("Nu s-a putut adauga inregistrarea", bindingResult.getAllErrors());
                throw new InvalidInputException("Parametrii invalizi");
            }

            uiModel.asMap().clear();

            programareService.create(programareDto);

            redirectAttributes.addFlashAttribute("saveResult", "ok-add");
            redirectAttributes.addFlashAttribute("succesMessage", "Programarea a fost efectuata cu succes, o sa primiti un mail cu informatiile programarii!");
            return "redirect:/programare";
        } catch (Exception e) {
            logger.error("Eroare la adaugarea inregistrarii", e);
            redirectAttributes.addFlashAttribute("saveResult", "failed-add");
            return "redirect:/programare";
        }
    }

}
