package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.Tecnico;
import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.TicketRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import com.soulcode.elashelp.Services.TicketService;
import com.soulcode.elashelp.Services.UsuarioService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
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

        if (optionalUsuario.isPresent()) {
            // Se o usuário for encontrado, busca os tickets associados a esse usuário
            List<Ticket> userTickets = ticketService.findTicketsByUsuario(usuario);

            // Adiciona os tickets ao modelo para serem exibidos na página
            model.addAttribute("tickets", userTickets);
            model.addAttribute("usuario", usuario);


            return "usuario/tickets";
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
            return "error";
        }
    }

    //mostrar o chamado detalhado para o usuario
    @GetMapping("usuario/{id}")
    public String showTicketDetailsToUser(@PathVariable Integer id, Model model) {
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            model.addAttribute("ticket", optionalTicket.get());
            return "usuario/detalha-ticket";
        } else {
            return "error";
        }
    }

//    @GetMapping("admin/{id}")
//    public String showTicketDetailsToAdmin(@PathVariable Integer id, Model model) {
//        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
//        if (optionalTicket.isPresent()) {
//            model.addAttribute("ticket", optionalTicket.get());
//            return "admin/detalha-ticket-todos";
//        } else {
//            return "error";
//        }
//    }

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

    //excluir um ticket
    @DeleteMapping("/excluir/{id}")
    public String excluirTicket(@PathVariable Integer id) {
        // Verifica se o ticket com o ID especificado existe
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            // Se o ticket existir, exclua-o
            ticketService.deleteTicket(id);
        }
        // Redireciona de volta para a página de listagem de tickets
        return "redirect:/admin/home";
    }

    @GetMapping("/editar/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Ticket> ticket = ticketService.findTicketById(id);
        if (ticket.isPresent()) {
            model.addAttribute("ticket", ticket.get());
            return "admin/detalha-ticket-todos";
        } else {
            model.addAttribute("error", "ticket não encontrado.");
            return "redirect:ticket/todos";
        }
    }

//    @GetMapping("/editar/{id}")
//    public String showEditForm(@PathVariable("id") Integer id, Model model) {
//        Ticket ticket = ticketService.getTicketById(id);
//        if (ticket == null) {
//            ticket = new Ticket();  // Cria um novo objeto Ticket se nenhum ticket existir com o ID fornecido
//        }
//        model.addAttribute("ticket", ticket);
//        return "editar-ticket";
//    }

    // Método POST para processar o formulário de edição
    @PostMapping("/editar/{id}")
    public String processEditForm(@PathVariable("id") Integer id, @ModelAttribute("ticket") Ticket ticket, RedirectAttributes redirectAttributes) {
        try {
            ticketService.updateTicket(id);
            redirectAttributes.addFlashAttribute("success", "Chamado atualizado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar técnico.");
        }
        return "redirect:/home" ;
    }

}