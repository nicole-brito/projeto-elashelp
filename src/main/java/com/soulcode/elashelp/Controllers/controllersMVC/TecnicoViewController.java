package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Services.TecnicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/tecnico")
@RequiredArgsConstructor
@Slf4j
public class TecnicoViewController {

    @Autowired
    private TecnicoService tecnicoService;

    @GetMapping("/cadastro")
    public String cadastroTecnico(@ModelAttribute("tecnico") final Tecnico tecnico) {
        return "cadastro-tecnico.html";
    }

    @PostMapping("/cadastro")
    public String createTecnico(@ModelAttribute Tecnico tecnico, Model model) {
        try {
            Tecnico createTecnico = tecnicoService.createTecnico(tecnico);
            model.addAttribute("tecnico", createTecnico);
            model.addAttribute("success", "Cadastro realizado com sucesso!");
            return "cadastro-tecnico";
        } catch (RuntimeException e) {
            log.error("Erro ao criar novo tecnico", e);
            model.addAttribute("error", e.getMessage());
            return "cadastro-tecnico";
        }
    }

    //Admin area
    // @PreAuthorize("admin(true)")
    @GetMapping("/todos")
    public String showAllTecnicos(Model model) {
        List<Tecnico> tecnicos = tecnicoService.findAll();
        model.addAttribute("tecnicos", tecnicos);
        return "admin/tecnicos";
    }

    // @PreAuthorize("admin(true)")
    @PostMapping("/editar/{matricula}")
    public String deleteTecnico(@PathVariable Long matricula, RedirectAttributes redirectAttributes) {
        try {
            tecnicoService.deleteById(matricula);
            redirectAttributes.addFlashAttribute("sucess", "Técnico deletado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao deletar técnico.");
        }
        return "redirect:/tecnico/todos";
    }


    // @PreAuthorize("admin(true)")
    @GetMapping("/editar/{matricula}")
    public String editarTecnico(@PathVariable Long matricula, Model model) {
        Tecnico tecnico = tecnicoService.findById(matricula);
        model.addAttribute("tecnico", tecnico);
        return "admin/editar-tecnico";
    }
}
