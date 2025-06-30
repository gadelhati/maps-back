package com.maps.controller;

import com.maps.exception.ApiError;
import com.maps.persistence.payload.request.DTORequestUser;
import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.service.ServiceUserAuth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @mail	gadelha.ti@gmail.com
 * @link	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class Controller {

    private final ServiceUserAuth serviceUserAuth;

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }
    @PostMapping("/signup")
    public ModelAndView signUp(@RequestParam String username, @RequestParam String email, @RequestParam String password, @RequestParam String captchaToken) {
        serviceUserAuth.register(username, email, password, captchaToken);
        return new ModelAndView("confirm");
    }
    @PostMapping("/signup2")
    public ResponseEntity<ApiError> signUp(@RequestBody @Valid DTORequestUser value) {
        serviceUserAuth.register(value.getUsername(), value.getEmail(), "1234", "");
        return ResponseEntity.accepted().body(new ApiError(HttpStatus.CREATED, "", ""));
    }
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
    @PostMapping("/signin")
    public ModelAndView signIn(@RequestParam String username, @RequestParam String password, @RequestParam String totpKey, @RequestParam String captchaToken) {
        try {
            DTOResponseToken token = serviceUserAuth.login(new DTORequestUserAuth(username, password, Integer.parseInt(totpKey), captchaToken));
            ModelAndView modelAndView = new ModelAndView("upload");
            modelAndView.addObject("token", token);
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("login");
            modelAndView.addObject("loginError", true);
            return modelAndView;
        }
    }
    @GetMapping("/resetPassword")
    public ModelAndView requiredPassword() {
        return new ModelAndView("resetPassword");
    }
    @PostMapping("/requiredPassword")
    public ModelAndView resetPassword(@RequestParam String username, @RequestParam String captchaToken) {
        serviceUserAuth.resetPassword(username, captchaToken);
        return new ModelAndView("confirm");
    }
    @GetMapping("/resetTotp")
    public ModelAndView requiredTotp() {
        return new ModelAndView("resetTotp");
    }
    @PostMapping("/requiredTotp")
    public ModelAndView resetTotp(@RequestParam String username, @RequestParam String captchaToken) throws Exception {
        serviceUserAuth.resetTotp(username, captchaToken);
        return new ModelAndView("confirm");
    }
    @GetMapping("/confirm")
    public ModelAndView confirm() {
        return new ModelAndView("confirm");
    }
    @GetMapping("/logout")
    public ModelAndView logout(Model model) {
        return new ModelAndView("logout");
    }
}