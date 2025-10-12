package com.service.auto.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String token) {
        log.info("Reset password link: http://localhost:7080/reset-password?token=" + token);
        String resetUrl = "http://localhost:7080/reset-password?token=" + token;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Resetare parola");
        message.setText("Acceseaza urmatorul link pentru a-ti reseta parola: " + resetUrl);
        mailSender.send(message);
    }
}
