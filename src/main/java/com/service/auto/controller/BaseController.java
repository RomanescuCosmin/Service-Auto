package com.service.auto.controller;


import com.service.auto.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BaseController {

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

}
