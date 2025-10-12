package com.service.auto.controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalViewModel {

    @ModelAttribute("isAuthenticated")
    boolean isAuthenticated(Authentication authentication) {
            return authentication != null && authentication.isAuthenticated()
                    && !(authentication instanceof AnonymousAuthenticationToken);
    }


    @ModelAttribute("displayName")
    public String displayName(Authentication authentication) {
        return isAuthenticated(authentication) ? authentication.getName() : null;
    }
}
