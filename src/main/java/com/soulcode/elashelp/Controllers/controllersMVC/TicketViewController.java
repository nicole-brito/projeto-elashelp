package com.soulcode.elashelp.Controllers.controllersMVC;

import com.soulcode.elashelp.Models.*;
import com.soulcode.elashelp.Repositories.TicketRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import com.soulcode.elashelp.Services.TicketService;
import com.soulcode.elashelp.Services.UsuarioService;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String showTicketsByUsuario(@PathVariable Long idUsuario, @RequestParam String email, Usuario usuario, Model model) {
        // Busca o usuário pelo ID

            Optional<Usuario> optionalUsuario = usuarioService.findUsuarioById(idUsuario);

        if (optionalUsuario.isPresent()) {
            // Se o usuário for encontrado, busca os tickets associados a esse usuário
            List<Ticket> userTickets = ticketService.findTicketsByUsuario(usuario);

            // Adiciona os tickets ao modelo para serem exibidos na página
            model.addAttribute("tickets", userTickets);
            model.addAttribute("idUsuario", idUsuario);

            //TODO refinir uma pagina ou rota para usuario
            return "tickets-tecnico";



//        essa é a rota correta do usuario:
//            return "/usuario/tickets";

        } else {
            return "redirect:/error"; // Redireciona para uma página de erro
        }
    }

    //o get abre a tela de novo chamado, com os parametros filtrados
//    @GetMapping("/new")
//    public String abrirNewTicketForm(@PathVariable Long idUsuario, Model model) {
//        model.addAttribute("ticket", new Ticket());
//        model.addAttribute("idUsuario", idUsuario);
//        return "novo-chamado";
//    }

    @GetMapping("/new")
    public String newTicket(Model model, Long idUsuario) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("idUsuario", idUsuario);
        return "novo-chamado";
    }

    @PostMapping("/new")
    public String createTicket(@ModelAttribute Ticket ticket, Model model) {
        try {
            Ticket saveTicket = ticketService.createTicket();
            model.addAttribute("ticket", ticket);
            model.addAttribute("success", "Cadastro realizado com sucesso!");
            return "redirect:/usuario/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "cadastro-tecnico";
        }
    }




//    //o post insere dados do novo chamado
//    @PostMapping("/new")
//    public String mostrarNewTicketForm(@PathVariable Long idUsuario, @ModelAttribute Ticket ticket, Model model) {
//        // Busca o usuário pelo ID
//        Optional<Usuario> optionalUsuario = usuarioService.findUsuarioById(idUsuario);
//
//        if (optionalUsuario.isPresent()) {
//            Usuario usuario = optionalUsuario.get();
//
//            // Atribui a data atual ao ticket antes de salvar
//            ticket.setData(new Date());
//
//            // Associa o usuário ao novo chamado
//            ticket.setUsuario(usuario);
//
//            // Salva o ticket no banco de dados
//            ticketService.createTicket(ticket,usuario);
//
//            // Redireciona para a página de visualização de tickets do usuário
//            return "redirect:/usuario/home";
//        } else {
//            // Redireciona para uma página de erro se o usuário não for encontrado
//            return "redirect:/error";
//        }
//    }

    //mostrar o chamado detalhado
    @GetMapping("/{id}")
    public String showTicketDetails(@PathVariable Integer id,Ticket ticket, Model model) {
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            model.addAttribute("ticket", optionalTicket.get());
            model.addAttribute("idUsuario", ticket.getUsuario());
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

    //visao do tecnico

    @GetMapping("/todos-tecnico")
    public String visao(Model model){
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("tickets", tickets);
        return "tickets-tecnico";
    }
////buscar por id do tecnico, nao funciona ainda
//    @PostMapping("/todos-tecnico/{id}")
//    public String updateTecnicoTicket(@PathVariable Integer id,Model model,@RequestParam Prioridade prioridade, @RequestParam Status status) {
//        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
//        if (optionalTicket.isPresent()) {
//            Ticket ticket = optionalTicket.get();
//            ticket.setPrioridade(prioridade);
//            ticket.setStatus(status);
//            ticketService.saveTicket(ticket); // Método para salvar as alterações no ticket
//        }
//        return "redirect:/tickets/todos-tecnico"; // Redireciona de volta para a lista de tickets
//    }
//alterar o ticket que lhe foi atribuido, nao funciona ainda

    @GetMapping("/tecnico-alterarticket/{id}")
    public String updateTicketDetails(@PathVariable Integer id,Ticket ticket, Model model) {
        Optional<Ticket> optionalTicket = ticketService.findTicketById(id);
        if (optionalTicket.isPresent()) {
            model.addAttribute("ticket", optionalTicket.get());
            return "solucionar-ticket";
        } else {
            return "erro";
        }
    }


    // Método GET para exibir o formulário de edição
    @GetMapping("/editar/{id}")
    public String mostrarEditarTicket(@PathVariable("id") Integer id, Model model) {
        Optional<Ticket> ticket = ticketService.findTicketById(id);
        if (ticket.isPresent()) {
            model.addAttribute("ticket", ticket.get());
            return "admin/detalha-ticket-todos";
        } else {
            model.addAttribute("error", "Técnico não encontrado.");
            return "redirect:/admin/home";
        }
    }

    // Método POST para processar o formulário de edição
//    @PostMapping("/editar/{id}")
//    public String editarTicket(@ModelAttribute("ticket") Ticket ticket, RedirectAttributes redirectAttributes) {
//        try {
//            ticketService.updateTicket(ticket.getId());
//            redirectAttributes.addFlashAttribute("success", "Ticket atualizado com sucesso!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar ticket.");
//        }
//        return "redirect:/admin/home";
//    }

    @PostMapping("/editar/{id}")
    public String editarTicket(@PathVariable("id") Integer id, @ModelAttribute("ticket") Ticket ticketForm, RedirectAttributes redirectAttributes) {
        Optional<Ticket> ticketOptional = ticketService.findTicketById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            // Atualize os campos do ticket com os valores do formulário
            ticket.setTitulo(ticketForm.getTitulo());
            ticket.setDescricao(ticketForm.getDescricao());
            ticket.setUsuario(ticketForm.getUsuario());
            ticket.setPrioridade(ticketForm.getPrioridade());
            ticket.setSetor(ticketForm.getSetor());
            ticket.setTecnico(ticketForm.getTecnico());
            ticket.setStatus(ticketForm.getStatus());
            // Salve o ticket atualizado
            ticketService.saveTicket(ticket);
            redirectAttributes.addFlashAttribute("success", "Ticket atualizado com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Ticket não encontrado.");
        }
        return "redirect:/admin/home";
    }

}