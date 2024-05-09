package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view/tickets")
public class TicketViewController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("/todos")
    public String showTicketsPage(Model model) {
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("tickets", tickets);
        return "tickets";  // Verifique se o arquivo tickets.html existe em src/main/resources/templates
    }

    @GetMapping("/new")
    public String showNewTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "novo-chamado";  // Verifique se o arquivo novo-chamado.html existe em src/main/resources/templates
    }
}