package com.soulcode.elashelp.Controllers;


import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Services.TecnicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tecnicos")
@RequiredArgsConstructor
@Slf4j
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping("/cadastro")

    public String cadastro(@ModelAttribute("tecnico") final Tecnico tecnico) {
        return "/tecnico/cadastro";
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> createTecnico(@RequestBody Tecnico tecnico) {
        try {
            Tecnico createTecnico = tecnicoService.createTecnico(tecnico);
            return new ResponseEntity<>(createTecnico, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            log.error("Erro ao criar novo usuário", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}