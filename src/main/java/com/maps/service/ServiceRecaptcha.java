package com.maps.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@Service
public class ServiceRecaptcha {

    @Value("${recaptcha.secret}")
    private String recaptchaSecret;
    @Value("${recaptcha.url}")
    private String recaptchaUrl;
    private double threshold = 0.5;
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean validateCaptcha(String captchaToken) {
        String params = String.format("secret=%s&response=%s", recaptchaSecret, captchaToken);
        ResponseEntity<Map> response = restTemplate.postForEntity(recaptchaUrl + "?" + params, null, Map.class);
        if (response.getBody() != null && response.getBody().containsKey("score")) {
            double score = (Double) response.getBody().get("score");
            return score >= threshold;
        }
        return false;
    }
}