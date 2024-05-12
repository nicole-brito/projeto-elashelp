package com.soulcode.elashelp.Controllers.controllersMVC;


import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.RedefinirSenhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {


    @Autowired
    LoginService loginService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private RedefinirSenhaService redefinirSenhaService;

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
            model.addAttribute("error", "Email ou senha inválidos!");
            return "login";
        }
    }

    @GetMapping("/escolhercadastro")
    public String escolherCadastro() {
        return "escolhercadastro";
    }


    @GetMapping("esqueceuasenha")
    public String esquece() {
        return "esqueceuasenha.html";
    }
    @GetMapping("redefinirsenha")
    public String redefinir() {
        return "redefinirsenha.html";
    }


    @PostMapping("/esqueceuasenha")
    public String esqueceuSenha(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
            try {
               redefinirSenhaService.enviarNotificacao(email);
                return "redirect:/redefinirsenha";
            } catch (Exception e) {
                e.printStackTrace();
                return "esqueceuasenha";
            }
        }
    @PostMapping("/redefinirsenha")
    public String redefinirSenha(@RequestParam String token, @RequestParam String senha, Model model) {
        model.addAttribute("senha",senha);
        model.addAttribute("resetToken",token);
        if (redefinirSenhaService.redefinicaoSenha(token, senha)) {
            return "redirect:/login";
        } else {
            model.addAttribute("mensagemErro", "Token inválido ou expirado."); // Adiciona mensagem de erro ao modelo
            return "redefinirsenha"; // Retorna à página de esqueceu a senha
        }
    }

    }

