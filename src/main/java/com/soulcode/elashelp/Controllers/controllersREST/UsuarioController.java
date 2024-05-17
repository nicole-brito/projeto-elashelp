package com.soulcode.elashelp.Controllers.controllersREST;

import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import com.soulcode.elashelp.Services.EmailService;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    //Metodo get
    @GetMapping()
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    //Metodo post
    @PostMapping()
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioService.createUsuario(usuario);
    }

    //Metodo delete
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<?> deleteById(@PathVariable Long idUsuario) {
        try {
            Usuario deleteById = usuarioService.deleteById(idUsuario);
            return new ResponseEntity<>(deleteById, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar usuário");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Metodo update
    @PutMapping("/{idUsuario}")
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
