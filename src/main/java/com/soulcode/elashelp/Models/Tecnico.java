package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tecnicos")
@Entity
public class Tecnico {
    @Id
    private Long matricula;

    private boolean admin;

    private String nome;

    private String email;

    @Enumerated(EnumType.STRING)
    private Setor setor;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tecnico")
    private List<Ticket> tickets;

}
