package com.service.auto.service;

import com.service.auto.dto.ModelAutoSimplifyDto;
import com.service.auto.entity.ModelAuto;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModelAutoService extends BaseService {

    private static final Logger logger = LoggerFactory.getLogger(ModelAutoService.class);

    public List<ModelAutoSimplifyDto> findModelsByMarca(Long marcaId) {
        logger.info("Se caută modelele pentru marca cu ID: {}", marcaId);
        List<ModelAuto> modele = modelAutoRepository.findByMarcaId(marcaId);

        return modele.stream()
                .map(m -> new ModelAutoSimplifyDto(m.getId(), m.getNumeModel()))
                .collect(Collectors.toList());
    }
}
