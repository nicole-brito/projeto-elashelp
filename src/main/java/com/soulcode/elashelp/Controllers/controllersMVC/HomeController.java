package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/sobre")
    public String sobre() {
        return "sobre";
    }

    @GetMapping("/admin/home")
    public String dash() {
        return "admin/dashboard";
    }

    @GetMapping("/tecnicos/home")
    public String homeTec() {
        return "tickets-tecnico";
    }

    @GetMapping("/usuario/home/{idUsuario}")
    public String homeUser(@PathVariable Integer idUsuario, Model model, Usuario usuario) {
        model.addAttribute("tickets", ticketService.findTicketsByUsuario(usuario));
        return "usuario/tickets";
    }





}