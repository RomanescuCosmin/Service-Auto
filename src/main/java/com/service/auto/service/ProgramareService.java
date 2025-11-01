package com.service.auto.service;

import com.service.auto.dto.ProgramareDto;
import com.service.auto.entity.Programare;
import com.service.auto.mapper.ProgramareMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProgramareService extends BaseService {

    private final Logger logger = LoggerFactory.getLogger(ProgramareService.class);

    public Programare create(ProgramareDto programareDto) {
        logger.info("creare programare cu parametrii: ", programareDto);
        Programare programare = ProgramareMapper.toEntity(programareDto);
        return programareRepository.merge(programare);
    }
}
