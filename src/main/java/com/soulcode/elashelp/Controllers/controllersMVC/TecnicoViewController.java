package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.TecnicoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tecnico")
@RequiredArgsConstructor
@Slf4j
public class TecnicoViewController {

    @Autowired
    private LoginService loginService;

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
            return "redirect:/login";
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
        return "admin/dashboard";
    }

//    @DeleteMapping("/excluir/{id}")
//    public String deleteTecnico(@PathVariable("id") Long id) {
//        tecnicoService.deleteById(id);
//        return "redirect:/tecnico/todos";
//    }

    // Método GET para exibir o formulário de edição
    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Tecnico> tecnico = Optional.ofNullable(tecnicoService.findById(id));
        if (tecnico.isPresent()) {
            model.addAttribute("tecnico", tecnico.get());
            return "admin/editar-tecnico";
        } else {
            model.addAttribute("error", "Técnico não encontrado.");
            return "redirect:/tecnico/todos";
        }
    }

    // Método POST para processar o formulário de edição
    @PostMapping("/editar/{id}")
    public String processEditForm(@ModelAttribute("tecnico") Tecnico tecnico, RedirectAttributes redirectAttributes) {
        try {
            tecnicoService.updateTecnico(tecnico);
            redirectAttributes.addFlashAttribute("success", "Técnico atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar técnico.");
        }
        return "redirect:/home";
    }



}
