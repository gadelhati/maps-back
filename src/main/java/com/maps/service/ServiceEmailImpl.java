package com.maps.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author Marcelo Ribeiro Gadelha
 * @email gadelha.ti@gmail.com
 * @website www.gadelha.eti.br
 **/

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceEmailImpl implements ServiceEmail {

    private final JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@gadelha.eti.br");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);
            log.info("Email sent successfully to: {} with subject: {}", to, subject);
        } catch (Exception e) {
            log.error("Failed to send email to: {} with subject: {} - Error: {}", to, subject, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
    
    public void sendHtmlMessageWithAttachment(String to, String subject, String htmlContent, byte[] attachmentData, String attachmentName, String mimeType) {
        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("noreply@gadelha.eti.br");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            helper.addAttachment(attachmentName, new ByteArrayResource(attachmentData), mimeType);
            emailSender.send(message);
            log.info("HTML email with attachment sent successfully to: {} with subject: {}", to, subject);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email with attachment to: {} with subject: {} - Error: {}", to, subject, e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
