package com.service.auto.service;
import com.service.auto.dto.ContactDto;
import com.service.auto.entity.Contact;
import com.service.auto.entity.User;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ContactService extends BaseService {

    private final Logger logger = LoggerFactory.getLogger(ContactService.class);

    public Contact create(ContactDto contactDto, Long userId) {
        logger.info("creare contact cu parametrii: ", contactDto);
        Contact contact = contactMapper.toContact(contactDto);
        User user = userRepository.getReference(userId);
        contact.setUser(user);
        return contactRepository.merge(contact);
    }
}
