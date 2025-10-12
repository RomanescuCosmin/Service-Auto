package com.service.auto.controller;


import com.service.auto.service.ContactService;
import com.service.auto.service.EmailService;
import com.service.auto.service.UserService;
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

}
