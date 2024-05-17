package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Status;
import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.TicketRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public List<Ticket> findTicketsByUsuario(Usuario usuario) {
        return ticketRepository.findByUsuario(usuario);
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket createTicket(Ticket ticket, Usuario usuario) {
       ticketRepository.findByUsuario(usuario);
        ticket.setUsuario(usuario);

        ticket = ticketRepository.save(ticket);
        return ticket;
    }

    public Ticket updateTicket(Integer id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        ticket.setTitulo(ticket.getTitulo());
        ticket.setDescricao(ticket.getDescricao());
        ticket.setStatus(ticket.getStatus());
        ticket.setPrioridade(ticket.getPrioridade());
        ticket.setData(ticket.getData());
        ticket.setSetor(ticket.getSetor());

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }

    public void deleteTickets(Integer id,Ticket ticket, Usuario usuario) {
        ticketRepository.findByUsuario(usuario);
        ticket.setUsuario(usuario);
        ticketRepository.deleteById(id);
    }

    public List<Ticket> getTicketsByTecnicoId(Integer idTecnico) {
        return ticketRepository.findByTecnicoIdTecnico(idTecnico);
    }

//    Requisições para os gráficos
    public Map<String, Long > getOpenTicketsBySector() {
        List<Ticket> tickets = ticketRepository.findAll();

        Map<String, Long> openTicketsBySector = tickets.stream()
                .filter(ticket -> Status.ABERTO.equals(ticket.getStatus()))
                .collect(Collectors.groupingBy(ticket -> ticket.getSetor().toString(), Collectors.counting()));

        log.info("Open tickets by sector: {}", openTicketsBySector);

        return openTicketsBySector;
    }

    public Map<String, Long> getTotalTicketsBySector() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getSetor().toString(), Collectors.counting()));
    }

    public Map<String, Long> getFinishedTicketsBySector() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .filter(ticket -> Status.FINALIZADO.equals(ticket.getStatus()))
                .collect(Collectors.groupingBy(ticket -> ticket.getSetor().toString(), Collectors.counting()));
    }

    public Map<String, Long> getTicketsByStatus() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getStatus().toString(), Collectors.counting()));
    }

    public Map<String, Long> getTicketsByPriority() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getPrioridade().toString(), Collectors.counting()));
    }


}