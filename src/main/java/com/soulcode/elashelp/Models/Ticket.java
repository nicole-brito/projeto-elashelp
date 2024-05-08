package com.soulcode.elashelp.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String prioridade;

    private Date data;

    private Status status;

    private String titulo;

    private String descricao;

    @Enumerated(EnumType.STRING)
    private Setor setor;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tecnico_matricula", referencedColumnName = "matricula", nullable = true) // a coluna pode ser nula
    private Tecnico tecnico;
}
