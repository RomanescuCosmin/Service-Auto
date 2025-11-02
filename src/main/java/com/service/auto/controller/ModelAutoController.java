package com.service.auto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/model")
public class ModelAutoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ModelAutoController.class);


    @GetMapping(value = "/byMarca/{marcaId}", produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> getModelsByMarca(@PathVariable("marcaId") Long marcaId) {
        logger.info("Cerere GET pentru modelele mărcii cu ID {}", marcaId);
        try {
            return okJsonResponse(modelAutoService.findModelsByMarca(marcaId));

        } catch (Exception e) {
            logger.error("failed to get response from getModelsByMarca", e);
            return internalError(e);
        }

    }

}
