package com.soulcode.elashelp.Controllers.controllersMVC;


import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.RedefinirSenhaService;
import com.soulcode.elashelp.config.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {


    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthenticationManager authenticationManager;

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

        UsernamePasswordAuthenticationToken usernamePassword;
        Authentication authentication;
        try {
            usernamePassword = new UsernamePasswordAuthenticationToken(email, senha);
            authentication = this.authenticationManager.authenticate(usernamePassword);
        } catch (Exception ex) {
            return "redirect:/unauthorized";
        }

        var role = authentication.getAuthorities().stream().findFirst().get().toString();
        var autenticado = authentication.isAuthenticated();

        Login usuarioLogado = loginRepository.findLoginByEmail(email);
        //TODO redefinir retornos em caso de administrador ou tenico ou usuario
        //se o retorno for com redirect precisar passar o email do usuario logado como o exemplo da roda de usuario
        if(autenticado && role.equals("ADMINISTRADOR")){
            return "index";
        } else if (autenticado && role.equals("TECNICO")){
            return "tickets-tecnico";
        } else if(role.equals("USUARIO") ) {
            return "redirect:/tickets/todos/" + usuarioLogado.getUsuario().getIdUsuario() + "?email=" + usuarioLogado.getEmail();
        } else return "login";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
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
        model.addAttribute("senha", senha);
        model.addAttribute("resetToken", token);
        if (redefinirSenhaService.redefinicaoSenha(token, senha)) {
            return "redirect:/login";
        } else {
            model.addAttribute("mensagemErro", "Token inválido ou expirado."); // Adiciona mensagem de erro ao modelo
            return "redefinirsenha"; // Retorna à página de esqueceu a senha
        }
    }
}



