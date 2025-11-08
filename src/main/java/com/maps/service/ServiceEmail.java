package com.maps.service;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

public interface ServiceEmail {
    
    void sendSimpleMessage(String to, String subject, String text);
    void sendHtmlMessageWithAttachment(String to, String subject, String htmlContent,
                                       byte[] attachmentData, String attachmentName, String mimeType);
}
