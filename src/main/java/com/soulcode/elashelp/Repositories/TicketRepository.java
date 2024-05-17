package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
//    List<Ticket> findByIdUsuario(Usuario usuario);

    // consulta sql nativa para buscar tickets por ID do usuário

    @Query("SELECT t FROM Ticket t WHERE t.usuario = :usuario")
    List<Ticket> findByUsuario(@Param("usuario") Usuario usuario);

    List<Ticket> findByTecnicoIdTecnico(Integer idTecnico);

}
