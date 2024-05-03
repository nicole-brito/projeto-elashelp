package com.soulcode.elashelp.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table( name = "ticket")
@Entity

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private Tecnico tecnico;


}
