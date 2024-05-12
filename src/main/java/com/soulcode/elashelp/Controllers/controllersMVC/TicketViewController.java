package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.TicketRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import com.soulcode.elashelp.Services.TicketService;
import com.soulcode.elashelp.Services.UsuarioService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tickets")
public class TicketViewController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/todos")
    public String showTicketsPage(Model model) {
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("tickets", tickets);
        return "/tickets";
    }

    @GetMapping("/new")
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "novo-chamado";
    }

    @GetMapping("/{id}")
    public String showTicketDetails(@PathVariable Integer id, Model model) {
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            model.addAttribute("ticket", optionalTicket.get());
            return "tickets/detalha-ticket";
        } else {
            return "erro";
        }
    }

}