package com.service.auto.repository;


import com.service.auto.entity.Contact;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository
public class ContactRepository extends BaseRepository<Contact> implements InitializingBean {

    @Override
    public void afterPropertiesSet() {
        this.setClazz(Contact.class);
    }

}
