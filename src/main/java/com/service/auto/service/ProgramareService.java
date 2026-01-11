package com.service.auto.service;

import com.service.auto.dto.ProgramareDto;
import com.service.auto.entity.FileStorage;
import com.service.auto.entity.Programare;
import com.service.auto.mapper.ProgramareMapper;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramareService extends BaseService {

    private final Logger logger = LoggerFactory.getLogger(ProgramareService.class);
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    public Programare create(ProgramareDto programareDto, Long userId, FileStorage fileStorage) {
        logger.info("creare programare cu parametrii: ", programareDto);
        Programare programare = ProgramareMapper.toEntity(programareDto, userId, fileStorage);
        return programareRepository.merge(programare);
    }

    public List<String> getBookedSlots(LocalDate fromDate) {
        return programareRepository.findBookedSlots(fromDate).stream()
                .filter(row -> row.length >= 3 && Objects.nonNull(row[0]) && Objects.nonNull(row[1]) && Objects.nonNull(row[2]))
                .map(row -> {
                    LocalDate date = (LocalDate) row[0];
                    Integer hour = (Integer) row[1];
                    Integer minute = (Integer) row[2];
                    return String.format("%s %02d:%02d", DATE_FORMAT.format(date), hour, minute);
                })
                .collect(Collectors.toList());
    }
}
