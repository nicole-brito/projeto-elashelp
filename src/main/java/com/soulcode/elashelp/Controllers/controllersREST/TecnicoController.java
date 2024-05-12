package com.soulcode.elashelp.Controllers;


import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Services.TecnicoService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/tecnicos")
@RequiredArgsConstructor
@Slf4j
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping("/{matricula}")
    public ResponseEntity<?> getTecnicoById(@PathVariable Long matricula) {
        try {
            Tecnico tecnico = tecnicoService.findById(matricula);
            if (tecnico != null) {
                return ResponseEntity.ok(tecnico);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            log.error("Erro ao buscar técnico por ID", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<?> deleteById(@PathVariable Long matricula) {
        try {
            String deleteById = tecnicoService.deleteById(matricula);
            return new ResponseEntity<>(deleteById, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar técnico");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<?> updateMatricula(@RequestBody Tecnico tecnico) {
        try {
            Tecnico updateTecnico = tecnicoService.updateTecnico(tecnico);
            return new ResponseEntity<>(updateTecnico, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao editar técnico", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}