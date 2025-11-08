package com.maps.controller;

import com.maps.persistence.payload.request.DTORequestUserAuth;
import com.maps.persistence.payload.response.DTOResponseToken;
import com.maps.service.ServiceAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author	Marcelo Ribeiro Gadelha
 * @email	gadelha.ti@gmail.com
 * @website	www.gadelha.eti.br
 **/

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ControllerThymeleaf {

    private final ServiceAuth serviceAuth;

    @GetMapping("/register")
    public ModelAndView register() {
        return new ModelAndView("register");
    }
    @PostMapping("/signup")
    public ModelAndView signUp(@RequestParam String username, @RequestParam String email/*, @RequestParam String captchaToken*/) {
        serviceAuth.register(username, email/*, captchaToken*/);
        return new ModelAndView("confirm");
    }
    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }
    @PostMapping("/signin")
    public ModelAndView signIn(@RequestParam String username, @RequestParam String password, @RequestParam String totpKey/*, @RequestParam String captchaToken*/) {
        try {
            DTOResponseToken token = serviceAuth.login(new DTORequestUserAuth(username, password, Integer.parseInt(totpKey), "captchaToken"));
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
    public ModelAndView resetPassword(@RequestParam String username/*, @RequestParam String captchaToken*/) {
        serviceAuth.resetPassword(username/*, captchaToken*/);
        return new ModelAndView("confirm");
    }
    @GetMapping("/resetTotp")
    public ModelAndView requiredTotp() {
        return new ModelAndView("resetTotp");
    }
    @PostMapping("/requiredTotp")
    public ModelAndView resetTotp(@RequestParam String username/*, @RequestParam String captchaToken*/) throws Exception {
        serviceAuth.resetTotp(username/*, captchaToken*/);
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
