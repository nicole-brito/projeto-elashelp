package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "usuarios")
@Entity
public class Usuario {
    @Id

    private String cpf;

    private String nome;

    private String email;

    private int telefone;

    @OneToMany
    private Ticket ticket;

}
