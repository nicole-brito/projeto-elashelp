package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Integer id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        ticket.setTitulo(ticketDetails.getTitulo());
        ticket.setDescricao(ticketDetails.getDescricao());
        ticket.setStatus(ticketDetails.getStatus());
        ticket.setPrioridade(ticketDetails.getPrioridade());
        ticket.setData(ticketDetails.getData());
        ticket.setSetor(ticketDetails.getSetor());

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }
}