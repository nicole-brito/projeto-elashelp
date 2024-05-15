package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.TecnicoService;
import com.soulcode.elashelp.Services.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TecnicoService tecnicoService;

    @ModelAttribute("nome")
    public String getNome(HttpSession session) {
        String email = (String) session.getAttribute("email");
        System.out.println(email);
        if (email != null) {
            return loginService.getUsuarioOuTecnico(email);
        } else {
            return null;
        }
    }

    @ModelAttribute
    public void showAllTickets(Model model) {
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("tickets", tickets);
    }

    @ModelAttribute
    public void showAllTecnicos(Model model) {
        List<Tecnico> tecnicos = tecnicoService.findAll();
        model.addAttribute("tecnicos", tecnicos);
    }

//    @ModelAttribute
//    public void showTicketById(Model model, @RequestParam(value = "id", required = false) Integer id) {
//        if (id != null) {
//            Ticket ticket = ticketService.getTicketById(id);
//            model.addAttribute("ticket", ticket);
//        }
//    }
}