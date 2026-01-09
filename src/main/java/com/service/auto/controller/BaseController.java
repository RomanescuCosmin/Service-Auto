package com.service.auto.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.service.auto.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BaseController {

    private final Logger logger = LoggerFactory.getLogger(BaseController.class);

    static final String CONTENT_TYPE = "Content-Type";
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8";

    @Autowired
    protected UserService userService;

    @Autowired
    protected EmailService emailService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ContactService contactService;

    @Autowired
    protected ProgramareService programareService;

    @Autowired
    protected MarcaService marcaService;

    @Autowired
    protected ModelAutoService modelAutoService;

    @Autowired
    protected FileStorageService fileStorageService;

    public ResponseEntity<String> okJsonResponse(Object response) {

        try {
            Gson gson = new GsonBuilder().setDateFormat(DATE_FORMAT).create();
            HttpHeaders headers = new HttpHeaders();
            headers.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);

            return new ResponseEntity<>(gson.toJson(response), headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("failed to serialize response", e);
            return internalError(e);
        }
    }

    protected ResponseEntity<String> internalError(Exception exception) {
        logger.error("Internal server error", exception);
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_TYPE, APPLICATION_JSON_CHARSET_UTF_8);
        return new ResponseEntity<>("Error: " + exception.getMessage(), headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
