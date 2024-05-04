package com.soulcode.elashelp.Repositories;

import com.soulcode.elashelp.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
