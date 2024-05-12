package com.soulcode.elashelp.Controllers.controllersMVC;


import com.soulcode.elashelp.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {


    @Autowired
    LoginService loginService;


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/entrar")
    public String processarLogin(Model model, String email, String senha) {
        model.addAttribute("email", email);
        model.addAttribute("senha", senha);
        if (loginService.verificarLogin(email, senha)) {
            return "entrar";
        } else {
            return "login";
        }
    }

    @GetMapping("/escolhercadastro")
    public String escolherCadastro() {
        return "escolhercadastro";
    }



}
