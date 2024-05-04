package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
//@RequestMapping("/usuario")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("cadastro")
    public String cadastro(@ModelAttribute("usuario") final Usuario usuario) {
        return "cadastro-usuario.html";
    }

    //Method add user
    @PostMapping("cadastro")
    public String createUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            Usuario createUsuario = usuarioService.createUsuario(usuario);
            model.addAttribute("usuario", createUsuario);
            return "login.html";
        } catch (RuntimeException e) {
            log.error("Erro ao criar novo usuário", e);
            model.addAttribute("error", e.getMessage());
            return "cadastro-usuario.html";
        }
    }

    //Method delete
    @DeleteMapping("/{cpf}")
    public ResponseEntity<?> deleteById(@PathVariable String cpf) {
        try {
            Usuario deleteById = usuarioService.deleteById(cpf);
            return new ResponseEntity<>(deleteById, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar usuário");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<?> updateUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario updateUsuario = usuarioService.updateUsuario(usuario);
            return new ResponseEntity<>(updateUsuario, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao editar usuário", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
