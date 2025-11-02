package com.service.auto.service;

import com.service.auto.entity.Marca;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MarcaService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(MarcaService.class);

    public List<Marca> findAll() {
        logger.info("Se caută toate marcile  {}");
        return marcaRepository.findAll();
    }
}
