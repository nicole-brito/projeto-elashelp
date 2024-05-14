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
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.time.LocalDate;

import java.security.Principal;
import java.util.Date;
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


    //mostrar lista de chamados do usuario que logou, no caso pegando o idUsuario dele
    @GetMapping("/todos/{idUsuario}")
    public String showTicketsByUsuario(@PathVariable Long idUsuario, Usuario usuario, Model model) {
        // Busca o usuário pelo ID
        Optional<Usuario> optionalUsuario = usuarioService.findUsuarioById(idUsuario);

        if (optionalUsuario != null) {
            // Se o usuário for encontrado, busca os tickets associados a esse usuário
            List<Ticket> userTickets = ticketService.findTicketsByUsuario(usuario);

            // Adiciona os tickets ao modelo para serem exibidos na página
            model.addAttribute("tickets", userTickets);
            model.addAttribute("usuario", usuario);


            return "tickets";
        } else {
            return "redirect:/error"; // Redireciona para uma página de erro
        }
    }

    //o get abre a tela de novo chamado, com os parametros filtrados
    @GetMapping("/new/{idUsuario}")
    public String abrirNewTicketForm(@PathVariable Long idUsuario, Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("idUsuario", idUsuario);
        return "novo-chamado";
    }

    //o post insere dados do novo chamado
    @PostMapping("/new/{idUsuario}")
    public String mostrarNewTicketForm(@PathVariable Long idUsuario, @ModelAttribute Ticket ticket, Model model) {
        // Busca o usuário pelo ID
        Optional<Usuario> optionalUsuario = usuarioService.findUsuarioById(idUsuario);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();

            // Atribui a data atual ao ticket antes de salvar
            ticket.setData(new Date());

            // Associa o usuário ao novo chamado
            ticket.setUsuario(usuario);

            // Salva o ticket no banco de dados
            ticketService.createTicket(ticket,usuario);

            // Redireciona para a página de visualização de tickets do usuário
            return "redirect:/tickets/todos/{idUsuario}";
        } else {
            // Redireciona para uma página de erro se o usuário não for encontrado
            return "redirect:/error";
        }
    }

    //mostrar o chamado detalhado
    @GetMapping("/{id}")
    public String showTicketDetails(@PathVariable Integer id, Model model) {
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            model.addAttribute("ticket", optionalTicket.get());
            return "detalha-ticket";
        } else {
            return "erro";
        }
    }

    //excluir um ticket
    @GetMapping("/excluir/{id}")
    public String excluirTicket(@PathVariable Integer id, @RequestParam Long idUsuario) {
        // Verifica se o ticket com o ID especificado existe
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            // Se o ticket existir, exclua-o
            ticketService.deleteTicket(id);
        }
        // Redireciona de volta para a página de listagem de tickets com o mesmo idUsuario
        return "redirect:/tickets/todos/" + idUsuario;
    }

}