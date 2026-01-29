package com.service.auto.service;

import com.service.auto.dto.ProgramareDto;
import com.service.auto.entity.Programare;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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


    public void sendEmailForAppointment(ProgramareDto programareDto) throws MessagingException {
        log.info("Sending email for appointment");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(programareDto.getEmail());
        helper.setSubject("Programare Service Auto");

        String htmlContent = loadEmailTemplate("email.html");

        htmlContent = htmlContent
                .replace("{{nume}}", programareDto.getNume())
                .replace("{{dataProgramare}}", programareDto.getDataProgramare().toString())
                .replace("{{ora}}",
                        programareDto.getOraProgramare() + ":" +
                                String.format("%02d", programareDto.getMinutProgramare()));

        helper.setText(htmlContent, true);
        helper.addInline(
                "serviceLogo",
                new ClassPathResource("static/image/email/service-logo.png"));

        mailSender.send(message);
    }


    private String loadEmailTemplate(String templateName) {
        try (InputStream inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("templates/email/" + templateName)) {

            if (inputStream == null) {
                throw new IllegalStateException("Template email inexistent");
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea template-ului email " + e);
        }
    }
}
