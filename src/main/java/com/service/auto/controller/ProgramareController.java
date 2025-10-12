package com.service.auto.controller;

import groovy.util.logging.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ProgramareController {


    private static final Logger log = LoggerFactory.getLogger(ProgramareController.class);

    @GetMapping("/programare")
    public String programare () {
        log.info("programare page");
        return "programare";
    }

}
