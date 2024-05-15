package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Services.LoginService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private LoginService loginService;

    @ModelAttribute("nome")
    public String getNome(HttpSession session) {
        String email = (String) session.getAttribute("email");
        System.out.println(email);
        if (email != null) {
            return loginService.getUsuarioOuTecnico(email);
        } else {
            return null;
        }
    }
}