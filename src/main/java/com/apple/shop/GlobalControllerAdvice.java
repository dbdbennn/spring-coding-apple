package com.apple.shop;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addGlobalAttributes(Authentication auth, Model model) {
        if (auth != null && auth.isAuthenticated()) {
            model.addAttribute("isAuthenticated", true);
        } else {
            model.addAttribute("isAuthenticated", false);
        }
    }
}
