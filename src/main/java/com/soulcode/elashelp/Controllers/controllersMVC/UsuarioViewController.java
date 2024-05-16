package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import com.soulcode.elashelp.Services.EmailService;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Slf4j
public class UsuarioViewController {

    @Autowired
    private final UsuarioService usuarioService;

    @GetMapping("cadastro")
    public String cadastro(@ModelAttribute("usuario") final Usuario usuario) {
        return "cadastro-usuario.html";
    }

    //Method add user
    @PostMapping("/cadastro")
    public String createUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            Usuario createUsuario = usuarioService.createUsuario(usuario);
            model.addAttribute("usuario", createUsuario);
            model.addAttribute("success", "Cadastro realizado com sucesso!");
            return "redirect:/login";
        } catch (RuntimeException e) {
            log.error("Erro ao criar novo usuário", e);
            model.addAttribute("error", e.getMessage());
            return "cadastro-usuario";
        }
    }

}
