package com.soulcode.elashelp.service;

import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.TicketRepository;
import com.soulcode.elashelp.Services.TicketService;
import com.soulcode.elashelp.Services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @InjectMocks
    TicketService ticketService;

    @Mock
    TicketRepository ticketRepository;

    @Mock
    UsuarioService usuarioService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldFindAllTickets() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findAll()).thenReturn(Arrays.asList(ticket1, ticket2));

        List<Ticket> tickets = ticketService.findAllTickets();

        assertEquals(2, tickets.size());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    public void shouldFindTicketById() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));

        Optional<Ticket> found = ticketService.findTicketById(1);

        assertEquals(ticket.getId(), found.get().getId());
        verify(ticketRepository, times(1)).findById(1);
    }

    @Test
    public void shouldReturnEmptyWhenTicketNotFoundById() {
        when(ticketRepository.findById(1)).thenReturn(Optional.empty());

        Optional<Ticket> found = ticketService.findTicketById(1);

        assertEquals(Optional.empty(), found);
        verify(ticketRepository, times(1)).findById(1);
    }

    @Test
    public void shouldFindTicketsByUsuario() {
        Usuario usuario = new Usuario();
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        when(ticketRepository.findByUsuario(usuario)).thenReturn(Arrays.asList(ticket1, ticket2));

        List<Ticket> tickets = ticketService.findTicketsByUsuario(usuario);

        assertEquals(2, tickets.size());
        verify(ticketRepository, times(1)).findByUsuario(usuario);
    }

    @Test
    public void shouldSaveTicket() {
        Ticket ticket = new Ticket();
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket saved = ticketService.saveTicket(ticket);

        assertEquals(ticket, saved);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void shouldCreateTicket() {
        Usuario usuario = new Usuario();
        Ticket ticket = new Ticket();
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket created = ticketService.createTicket(ticket, usuario);

        assertEquals(ticket, created);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void shouldUpdateTicket() {
        Ticket ticket = new Ticket();
        ticket.setId(1);
        when(ticketRepository.findById(1)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket updated = ticketService.updateTicket(1);

        assertEquals(ticket, updated);
        verify(ticketRepository, times(1)).findById(1);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    public void shouldDeleteTicket() {
        doNothing().when(ticketRepository).deleteById(1);

        ticketService.deleteTicket(1);

        verify(ticketRepository, times(1)).deleteById(1);
    }
}