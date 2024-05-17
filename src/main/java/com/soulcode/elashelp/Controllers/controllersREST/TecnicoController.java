package com.soulcode.elashelp.Controllers.controllersREST;


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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    // Get
    @GetMapping("/{idTecnico}")
    public ResponseEntity<?> getTecnicoById(@PathVariable Long idTecnico) {
        try {
            Tecnico tecnico = tecnicoService.findById(idTecnico);
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

    // Post
    @PostMapping()
    public Tecnico createTecnico(@RequestBody Tecnico tecnico) {
        return tecnicoService.createTecnico(tecnico);
    }

    // Delete
    @DeleteMapping("deletar/{idTecnico}")
    public ResponseEntity<?> deleteById(@PathVariable Long idTecnico) {
        try {
            tecnicoService.deleteById(idTecnico);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao deletar técnico");
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Put
    @PutMapping("atualizar/{idTecnico}")
    public ResponseEntity<?> updateid(@RequestBody Tecnico tecnico) {
        try {
            Tecnico updateTecnico = tecnicoService.updateTecnico(tecnico);
            return new ResponseEntity<>(updateTecnico, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("Erro ao editar técnico", e);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}