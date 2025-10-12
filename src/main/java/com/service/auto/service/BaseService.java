package com.service.auto.service;

import com.service.auto.mapper.ContactMapper;
import com.service.auto.repository.ContactRepository;
import com.service.auto.repository.RoleRepository;
import com.service.auto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected ContactRepository contactRepository;

    @Autowired
    protected ContactMapper contactMapper;
}
