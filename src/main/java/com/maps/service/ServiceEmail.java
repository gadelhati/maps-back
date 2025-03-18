package com.maps.service;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

public interface ServiceEmail {
    void sendSimpleMessage(String to, String subject, String text);
}