package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Services.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/usuario")
@RequiredArgsConstructor
@Slf4j
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/cadastro")
    public String cadastro(@ModelAttribute("usuario") final Usuario usuario) {
        return "/usuario/cadastro";
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> createBebida(@RequestBody Usuario usuario) {
        try {
            Usuario createUsuario = usuarioService.createUsuario(usuario);
            return new ResponseEntity<>(createUsuario, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Erro ao criar novo usuário", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



}
