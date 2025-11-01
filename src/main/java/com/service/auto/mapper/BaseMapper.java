package com.service.auto.mapper;

import com.service.auto.repository.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseMapper {

    protected static UserRepository userRepository;
    protected static FileStorageRepository fileStorageRepository;
    protected static MarcaRepository marcaRepository;
    protected static ModelAutoRepository modelAutoRepository;
    protected static ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepositoryLocal;

    @Autowired
    private FileStorageRepository fileStorageRepositoryLocal;

    @Autowired
    private MarcaRepository marcaRepositoryLocal;

    @Autowired
    private ModelAutoRepository modelAutoRepositoryLocal;

    @Autowired
    private ContactRepository contactRepositoryLocal;

    @PostConstruct
    public void init() {
        BaseMapper.userRepository = userRepositoryLocal;
        BaseMapper.fileStorageRepository = fileStorageRepositoryLocal;
        BaseMapper.marcaRepository = marcaRepositoryLocal;
        BaseMapper.modelAutoRepository = modelAutoRepositoryLocal;
        BaseMapper.contactRepository = contactRepositoryLocal;
    }
}
