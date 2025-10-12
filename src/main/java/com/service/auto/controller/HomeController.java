package com.service.auto.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    public final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("/")
    public String home(@RequestParam(value="loginSuccess", required=false) String ok,
                       Model model) {
        if (ok != null) {
            model.addAttribute("success", "Te-ai autentificat cu succes!");
        }
        return "home";
    }


    @GetMapping("/consultanta-daune")
    public String consultantaDaunePage(Model uiModel) {
        logger.info("consultantaDaunePage");
        return "consultanta-daune";
    }

    @GetMapping("/despre-noi")
    public String despreNoiPage(Model uiModel) {
        logger.info("despreNoiPage");
        return "despre-noi";
    }

    @GetMapping("/servicii")
    public String serviciiPage(Model uiModel) {
        logger.info("serviciiPage");
        return "servicii";
    }

    @GetMapping("/galerie")
    public String galeriePage(Model uiModel) {
        logger.info("galeriePage");
        return "galerie";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("error", "Nu ai permisiunea să accesezi această resursă.");
        return "access-denied"; // templates/access-denied.html
    }

}
