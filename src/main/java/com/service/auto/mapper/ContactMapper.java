package com.service.auto.mapper;

import com.service.auto.dto.ContactDto;
import com.service.auto.entity.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {


    public Contact toContact(ContactDto contactDto) {
        return Contact.builder()
                .id(contactDto.getId())
                .nume(contactDto.getNume())
                .email(contactDto.getEmail())
                .telefon(contactDto.getTelefon())
                .descriere(contactDto.getDescriere())
                .serieSasiu(contactDto.getSerieSasiu())
                .build();
    }
}
