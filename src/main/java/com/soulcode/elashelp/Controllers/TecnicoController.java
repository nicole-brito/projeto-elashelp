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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
//@RequestMapping("/tecnicos")
@RequiredArgsConstructor
@Slf4j
public class TecnicoController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping("cadastrotecnico")

    public String cadastroTecnico(@ModelAttribute("tecnico") final Tecnico tecnico) {
        return "cadastro-tecnico.html";
    }

//    @PostMapping("/cadastro")
//    public ResponseEntity<?> createTecnico(@RequestBody Tecnico tecnico) {
//        try {
//            Tecnico createTecnico = tecnicoService.createTecnico(tecnico);
//            return new ResponseEntity<>(createTecnico, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            log.error("Erro ao criar novo usuário", e);
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }}

    @PostMapping("/cadastrotecnico")
    public String createTecnico(@ModelAttribute Tecnico tecnico, Model model) {
        try {
            Tecnico createTecnico= tecnicoService.createTecnico(tecnico);
            model.addAttribute("tecnico", createTecnico);
            model.addAttribute("success", "Cadastro realizado com sucesso!");
            return "cadastro-tecnico";
        } catch (RuntimeException e) {
            log.error("Erro ao criar novo tecnico", e);
            model.addAttribute("error", e.getMessage());
            return "cadastro-tecnico";
        }
    }
    //Method get
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

    //Method delete
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