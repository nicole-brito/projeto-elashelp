package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "tecnicos")
public class Tecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idTecnico;

    private String matricula;

    private boolean admin;

    private String nome;

    private String sobrenome;

    private String email;

    private String senha;

    private String telefone;

    @Enumerated(EnumType.STRING)
    private Setor setor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tecnico")
    private List<Ticket> tickets;

    @OneToOne(mappedBy = "tecnico", cascade = CascadeType.ALL)
    private Login login;
}
