package com.service.auto.mapper;

import com.service.auto.dto.ContactDto;
import com.service.auto.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper extends BaseMapper {


    public static Contact toContact(ContactDto contactDto, Long userId) {
        return Contact.builder()
                .id(contactDto.getId())
                .user(userRepository.findById(userId))
                .nume(contactDto.getNume())
                .email(contactDto.getEmail())
                .telefon(contactDto.getTelefon())
                .descriere(contactDto.getDescriere())
                .serieSasiu(contactDto.getSerieSasiu())
                .build();
    }
}
