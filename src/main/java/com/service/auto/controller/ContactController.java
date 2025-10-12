package com.service.auto.controller;


import com.service.auto.dto.ContactDto;
import com.service.auto.exception.InvalidInputException;
import com.service.auto.security.CustomUserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class ContactController extends BaseController {


    @GetMapping(value = "/contact", produces = "text/html")
    public String contactPage(Model uiModel) {
        log.info("contactPage");

        ContactDto contact = new ContactDto();

        uiModel.addAttribute("contact", contact);
        uiModel.addAttribute("pageMode", "contact");
        return "contact";
    }

    @PostMapping(value = "/contact", produces = "text/html")
    public String addContact(@ModelAttribute("contact") ContactDto contactDto,
                             BindingResult bindingResult, Model uiModel,
                             HttpServletRequest httpServletRequest,
                             RedirectAttributes redirectAttributes) {

        log.info("post create Contact cu parametrii : ", contactDto);

        try {

            if (bindingResult.hasErrors()) {
                log.error("Nu s-a putut adaugat inregistrarea", bindingResult.getAllErrors());
                throw new InvalidInputException("Parametri invalizi");
            }

            uiModel.asMap().clear();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            CustomUserPrincipal principal = (CustomUserPrincipal) auth.getPrincipal();

            contactService.create(contactDto, principal.getId());

            redirectAttributes.addFlashAttribute("saveResult", "ok-add");
            redirectAttributes.addFlashAttribute("succesMessage", "Mesajul a fost trimis cu succes, un operator o să vă contacteze curând !");
            return "redirect:/contact";

        } catch (Exception e) {
            log.error("Eroare la adaugarea inregistrarii", e);
            redirectAttributes.addFlashAttribute("saveResult", "failed-add");
            redirectAttributes.addFlashAttribute("errorMessage", "Mesajul nu a putut fi trimis deoarece nu sunteți logat!");
            return "redirect:/contact";
        }

    }
}
