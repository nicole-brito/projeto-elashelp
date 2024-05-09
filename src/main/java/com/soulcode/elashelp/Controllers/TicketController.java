package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Models.Setor;
import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Integer id) {
        return ticketService.findTicketById(id)
                .map(ticket -> new ResponseEntity<>(ticket, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Ticket> createTicket(@RequestBody Ticket ticket) {
        // Definindo valores padrão ou nulos para campos não especificados
        if (ticket.getData() == null) ticket.setData(new Date());
        if (ticket.getSetor() == null) ticket.setSetor(Setor.ADMINISTRATIVO);

        Ticket newTicket = ticketService.saveTicket(ticket);
        return new ResponseEntity<>(newTicket, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable Integer id, @RequestBody Ticket ticketDetails) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDetails);
        return new ResponseEntity<>(updatedTicket, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Integer id) {
        try {
            ticketService.deleteTicket(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Requisição para os gráficos
    @GetMapping("open-by-sector")
    public Map<String, Long> getOpenTicketsBySector() {
        return ticketService.getOpenTicketsBySector();
    }

    @GetMapping("/total-by-sector")
    public Map<String, Long> getTotalTicketsBySector() {
        return ticketService.getTotalTicketsBySector();
    }

    @GetMapping("/finished-by-sector")
    public Map<String, Long> getFinishedTicketsBySector() {
        return ticketService.getFinishedTicketsBySector();
    }

    @GetMapping("/by-status")
    public Map<String, Long> getTicketsByStatus() {
        return ticketService.getTicketsByStatus();
    }
}