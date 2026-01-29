package com.service.auto.service;

import com.service.auto.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class BaseService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected ContactRepository contactRepository;

    @Autowired
    protected ProgramareRepository programareRepository;

    @Autowired
    protected ModelAutoRepository modelAutoRepository;

    @Autowired
    protected MarcaRepository marcaRepository;

    @Autowired
    protected Environment environment;

    @Autowired
    protected EmailService emailService;

    @Autowired
    protected FileStorageRepository fileStorageRepository;
}
