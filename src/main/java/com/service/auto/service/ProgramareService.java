package com.service.auto.service;

import com.service.auto.dto.ProgramareDto;
import com.service.auto.dto.ProgramareSlotDto;
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
                .map(this::formatSlot)
                .collect(Collectors.toList());
    }

    private String formatSlot(ProgramareSlotDto slot) {
        LocalDate date = slot.getDataProgramare();
        Integer hour = slot.getOraProgramare();
        Integer minute = slot.getMinutProgramare();
        return String.format("%s %02d:%02d", DATE_FORMAT.format(date), hour, minute);
    }

    public List<Programare> findProgramareByUserId(Long userId) {
        // TODO: Completează mesajul de log cu placeholder-e (ex: "{}", userId) pentru a vedea parametrii în loguri.
        logger.info("findProgramareByUserId cu parametrii: ", userId);
        return programareRepository.findProgramareByUserId(userId);
    }

    public Programare findById(Long id) {
        return programareRepository.findById(id);
    }
}
